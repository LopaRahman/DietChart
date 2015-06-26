package com.example.lutfihabiba.dietchartfinal1;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;
/**
 * Created by Lutfi Habiba on 26/06/2015.
 */
public class Add_Update_delete_Activity  extends ActionBarActivity
{
    Button add_btn;
    ListView DietChart_listview;
    ArrayList<DietChart> dietchart_data = new ArrayList<DietChart>();
    DietChart_Adapter cAdapter;
    DatabaseHandler db;
    String Toast_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_update_delete_activity);
        try {
            DietChart_listview = (ListView) findViewById(R.id.list);
            DietChart_listview.setItemsCanFocus(false);
            add_btn = (Button) findViewById(R.id.add_btn);

            Set_Referash_Data();

        } catch (Exception e) {
            // TODO: handle exception
            Log.e("some error", "" + e);
        }
        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent add_user = new Intent(Add_Update_delete_Activity.this,
                        MainActivity.class);
                add_user.putExtra("called", "add");
                add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(add_user);
                finish();
            }
        });

    }

    public void Set_Referash_Data() {
        dietchart_data.clear();
        db = new DatabaseHandler(this);
        ArrayList<DietChart> dietchart_array_from_db = db.Get_DietCharts();

        for (int i = 0; i < dietchart_array_from_db.size(); i++) {

            int tidno = dietchart_array_from_db.get(i).get_id();
            String catagory = dietchart_array_from_db.get(i).get_catagory();
            String date = dietchart_array_from_db.get(i).get_date();
            String time = dietchart_array_from_db.get(i).get_time();
            String catagoryValue = dietchart_array_from_db.get(i).get_catagoryValue();

            DietChart cnt = new DietChart();
            cnt.set_id(tidno);
            cnt.set_catagory(catagory);
            cnt.set_date(date);
            cnt.set_time(time);
            cnt.set_catagoryValue(catagoryValue);

            dietchart_data.add(cnt);
        }
        db.close();
        cAdapter = new DietChart_Adapter(Add_Update_delete_Activity.this, R.layout.listview_row,
                dietchart_data);
        DietChart_listview.setAdapter(cAdapter);
        cAdapter.notifyDataSetChanged();
    }

    public void Show_Toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Set_Referash_Data();

    }

    public class DietChart_Adapter extends ArrayAdapter<DietChart> {
        Activity activity;
        int layoutResourceId;
        DietChart user;
        ArrayList<DietChart> data = new ArrayList<DietChart>();

        public DietChart_Adapter(Activity act, int layoutResourceId,
                                 ArrayList<DietChart> data) {
            super(act, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.activity = act;
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            UserHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new UserHolder();
                holder.catagory = (TextView) row.findViewById(R.id.user_catagory_txt);
                holder.date = (TextView) row.findViewById(R.id.user_date_txt);
                holder.time = (TextView) row.findViewById(R.id.user_time_txt);
                holder.catagoryValue = (TextView) row.findViewById(R.id.user_catValue_txt);

                holder.edit = (Button) row.findViewById(R.id.btn_update);
                holder.delete = (Button) row.findViewById(R.id.btn_delete);
                row.setTag(holder);
            } else {
                holder = (UserHolder) row.getTag();
            }
            user = data.get(position);
            holder.edit.setTag(user.get_id());
            holder.delete.setTag(user.get_id());
            holder.catagory.setText(user.get_catagory());
            holder.date.setText(user.get_date());
            holder.time.setText(user.get_time());
            holder.catagoryValue.setText(user.get_catagoryValue());

            holder.edit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Log.i("Edit Button Clicked", "**********");

                    Intent update_user = new Intent(activity,
                            MainActivity.class);
                    update_user.putExtra("called", "update");
                    update_user.putExtra("USER_ID", v.getTag().toString());
                    activity.startActivity(update_user);

                }
            });
            holder.delete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(final View v) {
                    // TODO Auto-generated method stub

                    // show a message while loader is loading

                    AlertDialog.Builder adb = new AlertDialog.Builder(activity);
                    adb.setTitle("Delete?");
                    adb.setMessage("Are you sure you want to delete ");
                    final int user_id = Integer.parseInt(v.getTag().toString());
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok",
                            new AlertDialog.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // MyDataObject.remove(positionToRemove);
                                    DatabaseHandler dBHandler = new DatabaseHandler(
                                            activity.getApplicationContext());
                                    dBHandler.Delete_DietChart(user_id);
                                    Add_Update_delete_Activity.this.onResume();

                                }
                            });
                    adb.show();
                }

            });
            return row;

        }

        class UserHolder {
            TextView catagory;
            TextView date;
            TextView time;
            TextView catagoryValue;
            Button edit;
            Button delete;
        }

    }
}
