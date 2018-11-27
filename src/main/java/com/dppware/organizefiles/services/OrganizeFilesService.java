package com.dppware.organizefiles.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dppware.organizefiles.events.core.IFileEventListener;
import com.dppware.organizefiles.utils.FileType;
/**
 * The Organizator files service
 * @author dpena
 *
 */
public class OrganizeFilesService {
	/**
	 * Where to start to look for files
	 */
	private String SOURCE_FILES_ROOT_DIR = null;
	/**
	 * Where the files will be stored ordered
	 */
	private String DESTINATION_FILES_ROOT_DIR = null;
	/**
	 * The file extenesions that will be managed
	 */
	public static final Set<FileType> ACCEPTED_FILE_TYPES = new HashSet<FileType>();
	/**
	 * Listeners for this process
	 */
	private List<IFileEventListener> listeners = new ArrayList<IFileEventListener>();
	

	/**
	 * Main constructor
	 */
	public OrganizeFilesService() {
		super();
	}

	/**
	 * Main entrance execution
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
		Path root = Paths.get(SOURCE_FILES_ROOT_DIR);
		if (Files.isDirectory(root)) {
			processDirectory(root);
			this.listeners.forEach(l -> l.onFinish());//dispatch
		} else {
			System.err.println("The selected file is not a directory");
			this.listeners.forEach(l -> l.onError("The selected rootPath (" + root + ")is not a directory"));
		}
	}

	/**
	 * Main recursive processsing
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public void processDirectory(Path dir) throws IOException {
		if (Files.isDirectory(dir)) {
			Files.list(dir).forEach(f -> {
				try {
					// Verify isdirectory
					if (Files.isDirectory(f)) {
						processDirectory(f);
					} else {
						if (ACCEPTED_FILE_TYPES.contains(getFileType(f))) {
							// printInfoFiles(f);
							Path destination = getPathByDateAndType(f);
							dispatchEvent(f.toString() + " ---> saved on " + destination);
							boolean cre = destination.toFile().mkdirs();
							Path finalDestFile = Paths.get(String.format("%s/%s", getPathByDateAndType(f).toString(), f.getFileName()));
							Files.copy(f, finalDestFile);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					this.listeners.forEach(l -> l.onError(e.getMessage()));
				}
			});
		}
	}
	
	/**
	 * Return the file type associated with the file extension
	 * @param file
	 * @return
	 */
	public FileType getFileType(Path file) {
		String rawName = file.toString();
		int separatorCharExtensionIndex =rawName.lastIndexOf(".");
		return separatorCharExtensionIndex!= -1?FileType.getFileTypeByExtension(rawName.substring(separatorCharExtensionIndex)):FileType.UNKNOWN;
	}

	/**
	 * Calculate output Folder structure for the passed file
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Path getPathByDateAndType(Path file) throws IOException {
		BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
		SimpleDateFormat df = new SimpleDateFormat("dd_MM_YYYY");
		String destDir = df.format(attr.creationTime().toMillis());
		destDir = String.format("%s/%s/%s", DESTINATION_FILES_ROOT_DIR, destDir, getFileType(file).toString().toLowerCase());
		return Paths.get(destDir);

	}
	/**
	 * Print File Metadata
	 * @param file
	 * @throws IOException
	 */
	public void printInfoFiles(Path file) throws IOException {
		System.out.println("Leyendo file ----------------" + file.getFileName() + "------------");
		BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

		System.out.println("creationTime: " + attr.creationTime());
		System.out.println("lastAccessTime: " + attr.lastAccessTime());
		System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

		System.out.println("isDirectory: " + attr.isDirectory());
		System.out.println("isOther: " + attr.isOther());
		System.out.println("isRegularFile: " + attr.isRegularFile());
		System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
		System.out.println("size: " + attr.size());
		System.out.println("TYPE : " + getFileType(file));
	}
	/**
	 * Returns Destination Root Directory
	 * @return
	 */
	public String getSourceFilesRootDir() {
		return SOURCE_FILES_ROOT_DIR;
	}
	/**
	 * Set Destination Root Directory
	 * @param sourceRootDir
	 */
	public void setSourceFilesRootDir(String sourceRootDir) {
		SOURCE_FILES_ROOT_DIR = sourceRootDir;
	}
	/**
	 * Return Destination Root Directory
	 * @return
	 */
	public String getDestinationRootDir() {
		return DESTINATION_FILES_ROOT_DIR;
	}
	/**
	 * Set Destination Root Directory
	 * @param destinationRootDir
	 */
	public void setDestinationRootDir(String destinationRootDir) {
		DESTINATION_FILES_ROOT_DIR = destinationRootDir;
	}
	

	/**
	 * Add new Accepted file type
	 * @param fileType
	 */
	public static void addAcceptedFileTypes(FileType fileType) {
		ACCEPTED_FILE_TYPES.add(fileType);
	}

	/**
	 * Add new Event Listener
	 * 
	 * @param listener
	 */
	public void addListener(IFileEventListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * dispatch Event across all listeners
	 * 
	 * @param logInfo
	 */
	private void dispatchEvent(String logInfo) {
		this.listeners.forEach(l -> l.onFileGeneratedEvent(logInfo));
	}

}
