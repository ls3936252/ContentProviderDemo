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
		//�������URIɸѡ
		URI_MATCHER.addURI("com.example.contentproviderdemo.StudentProvider",
				"student", STUDENTS);
		//ʹ��ͨ���#��ƥ����������
		URI_MATCHER.addURI("com.example.contentproviderdemo.StudentProvider",
				"student/#", STUDENT);		
	}

	public StudentProvider() {

	}	
	
	@Override
	public boolean onCreate() {
		// ��ʼ��һ�����ݳ־ò�
		studentDao = new StudentDAO(getContext());
		Log.i(TAG, "---->>onCreate()������");
		return true;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Uri resultUri = null;
		//����Uri������Code
		int flag = URI_MATCHER.match(uri);
		if (flag == STUDENTS) {
			long id = studentDao.insertStudent(values);
			Log.i(TAG, "---->>����ɹ�, id="+id);
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
				//�������ݣ�ʹ��ContentUris�������������β��Id
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
		Log.i(TAG, "---->>ɾ���ɹ�,count="+count);
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
		Log.i(TAG, "---->>���³ɹ���count="+count);
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
		Log.i(TAG, "---->>��ѯ�ɹ���Count="+cursor.getCount());
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
		bundle.putString("returnCall", "call��ִ����");
		return bundle;
	}
}
