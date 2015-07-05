package com.example.contentproviderdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.test.AndroidTestCase;
import android.util.Log;

public class MyTest extends AndroidTestCase {

	public MyTest() {
		// TODO Auto-generated constructor stub

	}

	public void calltest() {
		ContentResolver contentResolver = getContext().getContentResolver();
		Uri uri = Uri
				.parse("content://com.example.contentproviderdemo.StudentProvider/student");
		Bundle bundle=contentResolver.call(uri, "method", null, null);
		String returnCall=bundle.getString("returnCall");
		Log.i("main", "-------------->" + returnCall);
	}

	public void insert() {
		ContentResolver contentResolver = getContext().getContentResolver();
		Uri uri = Uri
				.parse("content://com.example.contentproviderdemo.StudentProvider/student");
		ContentValues values = new ContentValues();
		values.put("name", "Demo");
		values.put("address", "HK");
		Uri returnuir = contentResolver.insert(uri, values);
		Log.i("main", "-------------->" + returnuir.getPath());
	}

	public void delete() {
		ContentResolver contentResolver = getContext().getContentResolver();
		// ɾ�����У�content://com.example.contentproviderdemo.StudentProvider/student
		Uri uri = Uri
				.parse("content://com.example.contentproviderdemo.StudentProvider/student/1");
		contentResolver.delete(uri, null, null);
	}

	public void deletes() {
		ContentResolver contentResolver = getContext().getContentResolver();
		Uri uri = Uri
				.parse("content://com.example.contentproviderdemo.StudentProvider/student");
		String where = "address=?";
		String[] where_args = { "HK" };
		contentResolver.delete(uri, where, where_args);
	}

	public void update() {
		ContentResolver contentResolver = getContext().getContentResolver();
		Uri uri = Uri
				.parse("content://com.example.contentproviderdemo.StudentProvider/student/2");
		ContentValues values = new ContentValues();
		values.put("name", "����");
		values.put("address", "�Ϻ�");
		contentResolver.update(uri, values, null, null);
	}

	public void updates() {
		ContentResolver contentResolver = getContext().getContentResolver();
		Uri uri = Uri
				.parse("content://com.example.contentproviderdemo.StudentProvider/student");
		ContentValues values = new ContentValues();
		values.put("name", "����");
		values.put("address", "����");
		String where = "address=?";
		String[] where_args = { "beijing" };
		contentResolver.update(uri, values, where, where_args);
	}

	public void query() {
		ContentResolver contentResolver = getContext().getContentResolver();
		Uri uri = Uri
				.parse("content://com.example.contentproviderdemo.StudentProvider/student/2");
		Cursor cursor = contentResolver.query(uri, null, null, null, null);
		while (cursor.moveToNext()) {
			Log.i("main",
					"-------------->"
							+ cursor.getString(cursor.getColumnIndex("name")));
		}
	}

	public void querys() {
		ContentResolver contentResolver = getContext().getContentResolver();
		Uri uri = Uri
				.parse("content://com.example.contentproviderdemo.StudentProvider/student");
		String where = "address=?";
		String[] where_args = { "����" };
		Cursor cursor = contentResolver.query(uri, null, where, where_args,
				null);
		while (cursor.moveToNext()) {
			Log.i("main",
					"-------------->"
							+ cursor.getString(cursor.getColumnIndex("name")));
		}
	}

}
