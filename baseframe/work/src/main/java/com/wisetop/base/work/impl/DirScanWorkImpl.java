/**
 * 处理类似扫描目录的工作
 */
package com.wisetop.base.work.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;

import com.wisetop.base.work.WorkParent;
import com.wisetop.npf.util.Common;

public class DirScanWorkImpl extends WorkParent {
	private File curFile = null;
	private String curcPath = "";    // 以srcDir根目录
	private String srcDir = "";
	private String filefilter = "";
	private String sorttype = "";	
	private boolean	ifScanChild = false;

	public static void main(String[] args){
		File f = new File("E:\\新建文件夹\\beg-200\\backup");
		File[] fileList = f.listFiles();
		Arrays.sort(fileList, new CompratorByLastModified("desc"));
		for(File f1:fileList){
			System.out.println(f1.getAbsolutePath());
		}
	}


	public void work() throws Exception {		
		System.out.println("work:" + srcDir);
		if(srcDir.isEmpty()){
			System.out.println("dir is null");
			return;
		}
		scanDir(srcDir,"");
		System.out.println("srcDir end:" + srcDir);
		log.debug("srcDir end:" + srcDir);
	}

	private void scanDir(String srcDir,String pdir) throws Exception{
		File f = new File(srcDir);
		if (f.isDirectory()) {
			if(ifScanChild){
				File[] fileList = f.listFiles();
				if(fileList != null){
					for(int i=0;i<fileList.length;i++){
						if(fileList[i].isDirectory()){
							scanDir(fileList[i].getAbsolutePath(),pdir + fileList[i].getName() + File.separator);
						}
					}					
				}
			}
			File[] fileList = f.listFiles(new Filter(filefilter));
			if(fileList != null){
				if (Common.isNotNull(sorttype)) {
					Arrays.sort(fileList, new CompratorByLastModified(sorttype));
				}

				for (File o : fileList) {
					curFile = o;
					curcPath = pdir;
					process.process();
				}					
			}
		
		}
	}
	
	public String getSrcDir() {
		return srcDir;
	}

	public void setSrcDir(String srcDir) {
		this.srcDir = srcDir;
	}

	public File getCurFile() {
		return curFile;
	}

	public void setCurFile(File curFile) {
		this.curFile = curFile;
	}

	public String getFilefilter() {
		return filefilter;
	}

	public void setFilefilter(String filefilter) {
		this.filefilter = filefilter;
	}	
	
	public boolean isIfScanChild() {
		return ifScanChild;
	}

	public void setIfScanChild(boolean ifScanChild) {
		this.ifScanChild = ifScanChild;
	}


	public String getSorttype() {
		return sorttype;
	}


	public void setSorttype(String sorttype) {
		this.sorttype = sorttype;
	}


	public String getCurcPath() {
		return curcPath;
	}


	public void setCurcPath(String curcPath) {
		this.curcPath = curcPath;
	}	
	
}

class Filter implements FilenameFilter {
	String filterString;

	public Filter(String fString) {
		this.filterString = fString;
	}

	public boolean accept(File dir, String name) {
		if ("".equals(filterString) || filterString == null) {
			return true;
		} else {
			return name.endsWith(filterString);
		}
	}

}

class CompratorByLastModified implements Comparator<File> {
	private String sortType = "";

	public CompratorByLastModified(){
		
	}
	public CompratorByLastModified(String sortType) {
		this.sortType = sortType;
	}

	public int compare(File f1, File f2) {
		if ("desc".equals(sortType)) {
			long diff = f1.lastModified() - f2.lastModified();
			if (diff > 0)
				return -1;
			else if (diff == 0)
				return 0;
			else
				return 1;
		} else {
			long diff = f1.lastModified() - f2.lastModified();
			if (diff > 0)
				return 1;
			else if (diff == 0)
				return 0;
			else
				return -1;
		}
	}

	public boolean equals(Object obj) {
		return true;
	}

}

