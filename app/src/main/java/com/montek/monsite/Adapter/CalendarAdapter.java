package com.montek.monsite.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.montek.monsite.FLprofileOnEmployerDashbord;
import com.montek.monsite.R;
import com.montek.monsite.model.CalendarCollection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import static com.montek.monsite.Adapter.ListAdapterEmployerSearch.CheckDates;
import static com.montek.monsite.Adapter.ListAdapterEmployerSearch.Duration;
import static com.montek.monsite.FLprofileOnEmployerDashbord.datediaglog;
import static com.montek.monsite.FLprofileOnEmployerDashbord.elapsedDays;
import static com.montek.monsite.FLprofileOnEmployerDashbord.elapsedHours;
import static com.montek.monsite.FLprofileOnEmployerDashbord.elapsedMonth;
import static com.montek.monsite.FLprofileOnEmployerDashbord.map;
import static com.montek.monsite.FLprofileOnEmployerDashbord.multiMap;
import static com.montek.monsite.FLprofileOnEmployerDashbord.printDifference;
import static com.montek.monsite.FLprofileOnEmployerDashbord.txv_clear;
import static com.montek.monsite.FLprofileOnEmployerDashbord.txv_selecteddate;
import static com.montek.monsite.Fragments.Employer_SearchFragment.hiredlist;
public class CalendarAdapter extends BaseAdapter {
	private Context context;
	private java.util.Calendar month;
	public GregorianCalendar pmonth; 
	public GregorianCalendar pmonthmaxset;
	private GregorianCalendar selectedDate;
	int firstDay;
	int maxWeeknumber;
	int maxP;
	int calMaxP;
	//int lastWeekDay;
	//int leftDays;
    public static String timearray="";
	public static HashSet<String> totalselectedays=new HashSet();
	TextView dayView;
	int mnthlength;
	String itemvalue;
	public static String curentDateString;
	DateFormat df;
	private ArrayList<String> items;
	public static List<String> day_string;
	private View previousView;
    public ArrayList<CalendarCollection>  date_collection_arr,date_selected_arr;
	public CalendarAdapter(Context context, GregorianCalendar monthCalendar, ArrayList<CalendarCollection> date_collection_arr, ArrayList<CalendarCollection> date_selected_arr) {
		try {
			this.date_collection_arr = date_collection_arr;
			this.date_selected_arr = date_selected_arr;
			CalendarAdapter.day_string = new ArrayList<String>();
			Locale.setDefault(Locale.US);
			month = monthCalendar;
			selectedDate = (GregorianCalendar) monthCalendar.clone();
			this.context = context;
			month.set(GregorianCalendar.DAY_OF_MONTH, 1);
			this.items = new ArrayList<String>();
			df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
			curentDateString = df.format(selectedDate.getTime());
			refreshDays();
		} catch (Exception e) {
		}
	}
	public void setItems(ArrayList<String> items) throws Exception  {
		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}
	public int getCount()  {
		return day_string.size();
	}
	public Object getItem(int position) {
		return day_string.get(position);
	}
	public long getItemId(int position) {
		return 0;
	}
	// create a new view for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		TextView dayView;
		try {
			if (convertView == null) { // if it's not recycled, initialize some
				// attributes
				LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.cal_item, null);
			}
			dayView = (TextView) v.findViewById(R.id.date);
			String[] separatedTime = day_string.get(position).split("-");
			String gridvalue = separatedTime[2].replaceFirst("^0*", "");
			if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
				dayView.setTextColor(Color.GRAY);
				dayView.setClickable(false);
				dayView.setFocusable(false);
			} else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
				dayView.setTextColor(Color.GRAY);
				dayView.setClickable(false);
				dayView.setFocusable(false);
			} else {
				// setting curent month's dates in blue color.
				dayView.setTextColor(Color.BLACK);
			}
			//Log.d("day_string",day_string+" "+ curentDateString);
			if (day_string.get(position).equals(curentDateString)) {
				//v.setBackgroundColor(Color.RED);
				dayView.setTextColor(Color.RED);
				v.setBackgroundColor(Color.WHITE);
			} else {
				//for background color
				v.setBackgroundColor(Color.WHITE);
			}
			dayView.setText(gridvalue);
			// create date string for comparison
			String date = day_string.get(position);
			if (date.length() == 1) {
				date = "0" + date;
			}
			String monthStr = "" + (month.get(GregorianCalendar.MONTH) + 1);
			if (monthStr.length() == 1) {
				monthStr = "0" + monthStr;
			}
			// show icon if date is not empty and it exists in the items array
				/*ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
				if (date.length() > 0 && items != null && items.contains(date)) {
					iw.setVisibility(View.VISIBLE);
				} else {
					iw.setVisibility(View.GONE);
				}
				*/
			if(datediaglog.equalsIgnoreCase("0")) {
				setselectedView(v, position, dayView);
			}
			setEventView(v, position, dayView);
		}catch (Exception e){}
		return v;
	}
	public View setSelected(View view,int pos) throws Exception  {
//		if (previousView != null) {
//		//previousView.setBackgroundColor(Color.WHITE);
//		}
		//view.setBackgroundColor(Color.CYAN);
		int len=day_string.size();
		if (len>pos) {
			if (day_string.get(pos).equals(curentDateString)) {
			}else{
				previousView = view;
			}
		}
		return view;
	}
	public void refreshDays() throws Exception {
		// clear items
		items.clear();
		day_string.clear();
		Locale.setDefault(Locale.US);
		pmonth = (GregorianCalendar) month.clone();
		// month start day. ie; sun, mon, etc
		firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
		// finding number of weeks in current month.
		maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
		// allocating maximum row number for the gridview.
		mnthlength = maxWeeknumber * 7;
		maxP = getMaxP(); // previous month maximum day 31,30....
		calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (GregorianCalendar) pmonth.clone();
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);
		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {

			itemvalue = df.format(pmonthmaxset.getTime());
			pmonthmaxset.add(GregorianCalendar.DATE, 1);
			day_string.add(itemvalue);
		}
	}
	private int getMaxP() throws Exception {
		int maxP;
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			pmonth.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		return maxP;
	}
	public void setEventView(View v,int pos,TextView txt) throws Exception {
		
		int len=CalendarCollection.date_collection_arr.size();
		Log.d("selectevent", String.valueOf(len));
		for (int i = 0; i < len; i++) {
			CalendarCollection cal_obj=CalendarCollection.date_collection_arr.get(i);
			String date=cal_obj.date;
			Log.d("datedatedate",date);
			int len1=day_string.size();
			if (len1>pos) {
			if (day_string.get(pos).equalsIgnoreCase(date)) {
				Log.d("datessss",date+" "+ day_string);
				if(cal_obj.event_message!="0" && hiredlist.equalsIgnoreCase("0") && Duration.equalsIgnoreCase("Hourly"))
				{
					//v.setBackgroundResource(R.drawable.rounded_calender_selecteditem); //set Background color to book horly dates
					Log.d("cal_obj.event_message",date+" "+ cal_obj.event_message);
				}
				else {
					v.setBackgroundResource(R.drawable.rounded_calender_item); //set Background color to event
				}
				//	txt.setTextColor(Color.BLUE);
			}
		}}
	}
	public void setselecteView(View v,int pos,TextView txt) throws Exception {
		int len=CalendarCollection.date_selected_arr.size();
		Log.d("selectview", String.valueOf(len));
		for (int i = 0; i < len; i++) {
			CalendarCollection cal_obj=CalendarCollection.date_selected_arr.get(i);
			String date=cal_obj.date;
			int len1=day_string.size();
			if (len1>pos) {

				if (day_string.get(pos).equalsIgnoreCase(date)) {
					Log.d("datessss",date+" "+ day_string);
					//v.setBackgroundColor(Color.WHITE);//set Background color to event
					v.setBackgroundResource(R.drawable.rounded_calender_selecteditem); //set Background color to event
					//	txt.setTextColor(Color.BLUE);
				}
			}}
	}

	public void getPositionList(final String date, final Activity act) throws Exception {

		int len= CalendarCollection.date_collection_arr.size();
		timearray="";
		for (int i = 0; i < len; i++)
		{
			CalendarCollection cal_collection=CalendarCollection.date_collection_arr.get(i);
			String event_date=cal_collection.date;
			Log.d("event_date",event_date+" "+date+len);
			final String event_message=cal_collection.event_message;

			if (date.equals(event_date) &&  hiredlist.equalsIgnoreCase("0"))
			{
				Log.d("event_datedddd",event_date+" "+date);
				String s="This date is booked!!";

                FLprofileOnEmployerDashbord.startdate="";
                FLprofileOnEmployerDashbord.enddate="";
				if(totalselectedays.size()!=0) {
					txv_selecteddate.setVisibility(View.VISIBLE);
				}
				else
				{
					txv_selecteddate.setVisibility(View.GONE);
				}
				if(event_message!="0")
				{
					Collection<String> message = multiMap.get(event_date);

					if( map.get(event_date).size()!=0) {
						timearray = String.valueOf(map.get(event_date));
					}
					s="This date is booked on "+message;
				}
				//Toast.makeText(context, "This date is booked please try another date "+event_date, Toast.LENGTH_LONG).show();
				 new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Date: "+event_date )
				    .setMessage(s)
					.setPositiveButton("OK",new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
						if(event_message!="0" && Duration.equalsIgnoreCase("Hourly")  ) {
							FLprofileOnEmployerDashbord.startdate=date.trim();
							txv_selecteddate.setText("You are selected date is: " + date);
							txv_selecteddate.setVisibility(View.VISIBLE);
						}
					}
					}).show();
			    	break;
		    }
		    else
			{
				if(datediaglog.equalsIgnoreCase("1") && hiredlist.equalsIgnoreCase("0"))
				{
					printDifference(curentDateString+" 00:00:00",date.trim()+" 00:00:00");
					if(!CheckDates(date.trim()+" 00:00:00",curentDateString+" 00:00:00") && (elapsedDays>=1 ||  elapsedMonth>=1 || elapsedHours>=24))
					{
						Log.d("event_datedddd333",event_date+" "+date);
						FLprofileOnEmployerDashbord.startdate=date.trim();
						txv_selecteddate.setVisibility(View.VISIBLE);
						txv_selecteddate.setText("You are selected date is: " + date);
						if (Duration.equalsIgnoreCase("Daily")) {
							if (date_collection_arr.toString().contains(date)) {
							}
							else {
								Log.d("datefalse", "falase");
								Log.d("datessssss", date_collection_arr.toString() + (date));
								totalselectedays.add(FLprofileOnEmployerDashbord.startdate.trim());
							}
							txv_selecteddate.setText("You are selected date are: " + totalselectedays.toString());
							txv_clear.setVisibility(View.VISIBLE);
						} else {
							txv_clear.setVisibility(View.GONE);
						}
					} else{
						toast("Candidates Will be available after 24hour's of current date.");
						txv_selecteddate.setVisibility(View.GONE);
						return;
					}
				}
				else if(datediaglog.equalsIgnoreCase("0") && hiredlist.equalsIgnoreCase("0"))
				{
					if(Duration.equalsIgnoreCase("Monthly") || Duration.equalsIgnoreCase("Hourly")) {
						new AlertDialog.Builder(context)
								.setIcon(android.R.drawable.ic_dialog_alert)
								.setTitle("Date: "+date)
								.setMessage("You Cannot Change Enddate !!")
								.setPositiveButton("OK",new DialogInterface.OnClickListener(){
									public void onClick(DialogInterface dialog, int which)
									{
										dialog.dismiss();
									}
								}).show();
					     	return;
				      }else {
						 Log.d("datedate",date);
						FLprofileOnEmployerDashbord.enddate=date.trim();
						txv_selecteddate.setVisibility(View.VISIBLE);
						txv_selecteddate.setText("You are selected date is: " + date);
					  }
				}
            }
		}
	}

	  void toast(String s)
	{
		Toast toast= Toast.makeText(context,s, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}