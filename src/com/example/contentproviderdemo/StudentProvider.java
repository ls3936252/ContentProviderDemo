package com.example.contentproviderdemo;

import com.example.dao.StudentDAO;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class StudentProvider extends ContentProvider {

	private final String TAG = "main";
	private StudentDAO studentDao = null;
	private static final UriMatcher URI_MATCHER = new UriMatcher(
			UriMatcher.NO_MATCH);
	private static final int STUDENT = 1;
	private static final int STUDENTS = 2;
	static {
		//添加两个URI筛选
		URI_MATCHER.addURI("com.example.contentproviderdemo.StudentProvider",
				"student", STUDENTS);
		//使用通配符#，匹配任意数字
		URI_MATCHER.addURI("com.example.contentproviderdemo.StudentProvider",
				"student/#", STUDENT);		
	}

	public StudentProvider() {

	}	
	
	@Override
	public boolean onCreate() {
		// 初始化一个数据持久层
		studentDao = new StudentDAO(getContext());
		Log.i(TAG, "---->>onCreate()被调用");
		return true;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri resultUri = null;
		//解析Uri，返回Code
		int flag = URI_MATCHER.match(uri);
		if (flag == STUDENTS) {
			long id = studentDao.insertStudent(values);
			Log.i(TAG, "---->>插入成功, id="+id);
			resultUri = ContentUris.withAppendedId(uri, id);
		}
		return resultUri;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = -1;
		try {
			int flag = URI_MATCHER.match(uri);
			switch (flag) {
			case STUDENT:
				// delete from student where id=?
				//单条数据，使用ContentUris工具类解析出结尾的Id
				long id = ContentUris.parseId(uri);
				String where_value = "id = ?";
				String[] args = { String.valueOf(id) };
				count = studentDao.deleteStudent(where_value, args);
				break;
			case STUDENTS:
				count = studentDao.deleteStudent(selection, selectionArgs);				
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i(TAG, "---->>删除成功,count="+count);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		int count = -1;
		try {			
			int flag = URI_MATCHER.match(uri);
			switch (flag) {
			case STUDENT:
				long id = ContentUris.parseId(uri);
				String where_value = " id = ?";
				String[] args = { String.valueOf(id) };
				count = studentDao.updateStudent(values, where_value, args);
				break;
			case STUDENTS:
				count = studentDao.updateStudent(values, selection,
						selectionArgs);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i(TAG, "---->>更新成功，count="+count);
		return count;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Cursor cursor = null;
		try {
			int flag = URI_MATCHER.match(uri);
			switch (flag) {
			case STUDENT:
				long id = ContentUris.parseId(uri);
				String where_value = " id = ?";
				String[] args = { String.valueOf(id) };
				cursor = studentDao.queryStudents(where_value, args);
				break;
			case STUDENTS:
				cursor = studentDao.queryStudents(selection, selectionArgs);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i(TAG, "---->>查询成功，Count="+cursor.getCount());
		return cursor;
	}


	@Override
	public String getType(Uri uri) {
//		int flag = URI_MATCHER.match(uri);
//		String type = null;
//		switch (flag) {
//		case STUDENT:
//			type = "vnd.android.cursor.item/student";
//			Log.i(TAG, "----->>getType return item");
//			break;
//		case STUDENTS:
//			type = "vnd.android.cursor.dir/students";
//			Log.i(TAG, "----->>getType return dir");
//			break;
//		}
//		return type;
		return null;
	}
	@Override
	public Bundle call(String method, String arg, Bundle extras) {
		Log.i(TAG, "------>>"+method);
		Bundle bundle=new Bundle();
		bundle.putString("returnCall", "call被执行了");
		return bundle;
	}
}
