package org.seventhgroup.dto;

import java.io.Serializable;

//泛型类，便于包装数据
public class Result<T> implements Serializable{
	//是否操作成功
	private boolean success;
	//向前端传递的信息
	private String message;
	//向前端传递的数据
	private T data;
	public Result(boolean success, String message) {
		super();
		this.success=success;
		this.message = message;
	}
	public Result(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}