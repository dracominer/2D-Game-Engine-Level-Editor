package com.engine.toolbox;

public class ResourceLocation {

	private String parentPath;
	private String fileName;
	private String fileEntension;

	public ResourceLocation(String parentPath, String fileName, String fileEntension) {
		this.parentPath = parentPath;
		this.fileName = fileName;
		this.fileEntension = fileEntension;
	}

	public static ResourceLocation get(String path) {
		String[] parts = path.split("/");
		String[] nameParts = parts[parts.length - 1].split(".");
		String fileName = nameParts[0];
		String fileEntension = "." + nameParts[1];
		String parentPath = "/";
		for (int i = 0; i < nameParts.length - 1; i++) {
			parentPath += nameParts[i] + "/";
		}
		return new ResourceLocation(parentPath, fileName, fileEntension);
	}

	public String toString() {
		return getPath();
	}

	/**
	 * @return the parentPath
	 */
	public String getParentPath() {
		return parentPath;
	}

	/**
	 * @param parentPath the parentPath to set
	 */
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileEntension
	 */
	public String getFileEntension() {
		return fileEntension;
	}

	/**
	 * @param fileEntension the fileEntension to set
	 */
	public void setFileEntension(String fileEntension) {
		this.fileEntension = fileEntension;
	}

	public String getPath() {
		return parentPath + fileName + fileEntension;
	}

}
