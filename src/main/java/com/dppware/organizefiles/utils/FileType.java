package com.dppware.organizefiles.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
/**
 * Distinct FileTypes
 * @author dpena
 *
 */
public enum FileType {
	/**
	 * Unknowm extension files
	 */
	UNKNOWN (Collections.emptyList()),
	/**
	 * Extensions associated to image files
	 */
	IMAGE(Arrays.asList(".png", ".gif", ".jpg", ".jpeg", ".tiff", ".bmp", ".exif")),
	/**
	 * Extensions associated to video files
	 */
	VIDEO(Arrays.asList(".mp4", ".mov", ".mkv", ".flv", ".mpg", ".mpeg", ".3gp", ".asf", ".avi", ".wmv")),
	/**
	 * Extensions associated to audio files
	 */
	AUDIO(Arrays.asList(".mp3", ".wma", ".wav", ".aiff", ".acc", ".flac", ".m4a", ".mid", ".midi")),
	/**
	 * Extensions associated to compressed files
	 */
	COMPRESSED(Arrays.asList(".tar", ".tar.gz", ".zip")),
	/**
	 * Extensions associated to temporal files
	 */
	TEMPORAL(Arrays.asList(".tmp", ".lock"));
	
	/**
	 * Image type extensions
	 */
	List<String> extensions;
	
	private FileType(List<String> extensions) {
		this.extensions = extensions;
	}
	
	
	/**
	 * Return the Filetype associated with the passed extension
	 * @param extension
	 * @return
	 */
	public static FileType getFileTypeByExtension(String extension) {
		FileType type = null;
		Iterator<FileType> it = Arrays.asList(FileType.values()).iterator();
		while(it.hasNext() && type == null) {
			FileType ft = it.next();
			if(ft.extensions.contains(extension)) {
				type=ft;
			}
		}
		return type !=null ?type:FileType.UNKNOWN;
	}
	
}

