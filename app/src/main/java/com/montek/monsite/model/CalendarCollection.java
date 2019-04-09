package com.montek.monsite.model;
import java.util.ArrayList;
public class CalendarCollection {
	public String date="";
	public String event_message="";
	public static ArrayList<CalendarCollection> date_selected_arr=new ArrayList<CalendarCollection>();;
	public static ArrayList<CalendarCollection> date_collection_arr=new ArrayList<CalendarCollection>();

	public CalendarCollection(String date, String event_message){
			this.date=date;
		   this.event_message=event_message;
	}
	@Override
	public String toString() {
		return  date;
	}

	public boolean equals(Object obj){
		if (obj instanceof CalendarCollection) {
			CalendarCollection pp = (CalendarCollection) obj;
			return (pp.date.equals(this.date) );
		} else {
			return false;
		}
	}
}
