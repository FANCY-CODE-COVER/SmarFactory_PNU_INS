package util;

public class CustomLogger {
	// �α׸� ����� ���� Ŭ����
	public static void printLog(Object object, String tag,String message) {
		System.out.println(tag+" : "+object.getClass().toString()+" - "+message);		
	}
	public static void printLogCount(Object object, String tag,String message, int size) {
		System.out.println(tag+" : "+object.getClass().toString()+" - "+message+" : "+size);		
	}
}
