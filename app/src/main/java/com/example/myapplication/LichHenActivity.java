package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import androidx.activity.result.ActivityResultCallback;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class LichHenActivity extends AppCompatActivity {
    ListView listView, listViewTK;
    FloatingActionButton add;

    ArrayList<GhiChu> ls = new ArrayList<>();
    ArrayList<GhiChu> lstk = new ArrayList<>();
    GhiChuAdapter ghiChuAdapter;
    GhiChuDB ghiChuDB;

    ImageButton back;
    EditText timkiem;
    DateFormat formatter = new SimpleDateFormat("HH:mm");
    SimpleDateFormat formatterDay = new SimpleDateFormat("dd/MM/yyyy");
    public static final String INSERT_A = "insert";
    public static final String UPDATE = "update";
    //public static final String FILTER = "filter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addlichhen);
        listView = findViewById(R.id.lvghichu);
        listViewTK = findViewById(R.id.lvghichutk);
        add = findViewById(R.id.add);

        back=findViewById(R.id.back);

        timkiem = findViewById(R.id.timkiem);
        getlsGhiChu();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LichHenActivity.this, addLichHenActivity2.class);
                intent.setAction(INSERT_A);
                AResult.launch(intent);
            }
        });
        // đóng-----------------
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        timkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s=timkiem.getText().toString();
                Search(s);
            }
        });
        registerForContextMenu(listView);
    }

                /*case R.id.sort:
                Collections.sort(ls, new Comparator<GhiChu>() {
                    @Override
                    public int compare(GhiChu ghiChu, GhiChu t1) {
                        return ghiChu.getNgaythang().compareTo(t1.getNgaythang());
                    }
                });
                ghiChuAdapter.notifyDataSetChanged();
                return true;*/


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lvghichu) {
            getMenuInflater().inflate(R.menu.menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int ps = acmi.position;
        GhiChu ghiChu = ghiChuAdapter.getItem(ps);
        switch (item.getItemId()) {
            case R.id.upd:
                Toast.makeText(LichHenActivity.this, "update ghi chu: " + ghiChu.getTieude(), Toast.LENGTH_LONG);
                Intent intent = new Intent(LichHenActivity.this, addLichHenActivity2.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ghiChu", ghiChu);
                intent.putExtras(bundle);
                intent.setAction(UPDATE);
                UResult.launch(intent);
                break;
            case R.id.del:
                Toast.makeText(LichHenActivity.this, "delete ghi chu: " + ghiChu.getTieude(),
                        Toast.LENGTH_LONG).show();
                AlertDialog.Builder delDialog = new AlertDialog.Builder(LichHenActivity.this);
                delDialog.setTitle("Xoa ghi chu...");
                delDialog.setCancelable(true);
                delDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(LichHenActivity.this, "You choose cancel button", Toast.LENGTH_LONG).show();
                        dialogInterface.cancel();
                    }
                });
                delDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ghiChuDB = new GhiChuDB(LichHenActivity.this);
                        ghiChuDB.delGhiChu(ghiChu.getTieude());
                        dialogInterface.dismiss();
                        getlsGhiChu();
                    }
                });
                AlertDialog alertDialog = delDialog.create();
                alertDialog.show();
                break;
        }
        return true;
    }

    private void getlsGhiChu() {
        ghiChuDB = new GhiChuDB(LichHenActivity.this);
        ls = ghiChuDB.getALLGhiChu();
        ghiChuAdapter = new GhiChuAdapter(LichHenActivity.this, 0, ls);
        listView.setAdapter(ghiChuAdapter);
    }

    ActivityResultLauncher<Intent> AResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        String tieude = data.getStringExtra("tieude");
                        String noidung = data.getStringExtra("noidung");
                        String ngay = data.getStringExtra("ngaythang");
                        //Test
                        String gio = data.getStringExtra("gio");
                        String tgianthuc = data.getStringExtra("thoigian");

                        ghiChuDB = new GhiChuDB(LichHenActivity.this);
//                        ghiChuDB.insGhichu(new GhiChu(tieude,ngay,noidung));


                        //Test
                        ghiChuDB.insGhichu(new GhiChu(tieude, ngay, noidung, gio,tgianthuc));
                        Toast.makeText(LichHenActivity.this, "[" + tieude + ": " + noidung + " " + ngay + "]", Toast.LENGTH_LONG).show();
                        getlsGhiChu();
                    }
                }
            });
    ActivityResultLauncher<Intent> UResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        String tieude = data.getStringExtra("tieude");
                        String noidung = data.getStringExtra("noidung");
                        String ngay = data.getStringExtra("ngaythang");
                        String tgianthuc= data.getStringExtra("thoigian");
                        //
                        String gio = data.getStringExtra("gio");
                        ghiChuDB = new GhiChuDB(LichHenActivity.this);
                        ghiChuDB.updateGhichu(new GhiChu(tieude, ngay, noidung, gio,tgianthuc), data.getStringExtra("key"));
                        Toast.makeText(LichHenActivity.this, "[" + tieude + ": " + noidung + " " + ngay + "]", Toast.LENGTH_LONG).show();
                        getlsGhiChu();
                    }
                }
            });
    ActivityResultLauncher<Intent> FResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        //Lấy thời gian từ activity filter
                        String afterTime = data.getStringExtra(FilterActivity.AFTER_TIME_EXTRA_KEY);
                        String beforeTime = data.getStringExtra(FilterActivity.BEFORE_TIME_EXTRA_KEY);
                        String afterDate = data.getStringExtra(FilterActivity.AFTER_DATE_EXTRA_KEY);
                        String beforeDate = data.getStringExtra(FilterActivity.BEFORE_DATE_EXTRA_KEY);
                        Log.e("Date1", afterDate);
                        Log.e("Date2", beforeDate);
                        // gọi hàm filter
                        if(afterTime.isEmpty() == false || beforeTime.isEmpty() == false || (afterTime.isEmpty() == false && beforeTime.isEmpty() == false)){
                            filterByTime(afterTime, beforeTime);
                        }
                        else{
                            filterbyday(afterDate, beforeDate);
                        }
                    }
                }
            });

    public void Search(String s) {
        ghiChuAdapter = new GhiChuAdapter(LichHenActivity.this, 0, lstk);
        s = s.toUpperCase();
        lstk.clear();
        int k = 0;
        for (int i = 0; i < ls.size(); i++) {
            GhiChu g = ls.get(i);
            //Tìm kiếm theo gì thì get cái đó
            String td = g.getTieude().toUpperCase();
            //Nếu tìm được thì đổi chỗ lên đầu
            /*if (td.indexOf(s) >= 0) {
                ls.set(i, ls.get(k));
                ls.set(k, g);
                k++;
            } */
            //Nếu tìm được thì hiện lên
            if (td.indexOf(s) >= 0) {
                lstk.add(g);
            }
        }
            listViewTK.setAdapter(ghiChuAdapter);
            listView.setVisibility(View.GONE);
            listViewTK.setVisibility(View.VISIBLE);
            ghiChuAdapter.notifyDataSetChanged();


            // 1 2 3 //s = 3
            // k=0 i=0 g=ls0
            // k=0 i=1 g=ls1
            // k=0 i=2 g=ls2 t -> set(2, ls0) // 1 2 1 -> set(0,ls2) // 3 2 1
            //ghiChuAdapter.notifyDataSetChanged();

    }


    public ArrayList<GhiChu> FilterByTime(String afterTime, String beforeTime) {
        final String AFTER_TIME = "AFTER_TIME";
        final String BEFORE_TIME = "BEFORE_TIME";
        final String IN_RANGE = "IN_RANGE";
        String filter_case = "";
        if (!afterTime.isEmpty() && beforeTime.isEmpty()) {
            filter_case = AFTER_TIME;
        } else if (afterTime.isEmpty() && !beforeTime.isEmpty()) {
            filter_case = BEFORE_TIME;
        } else if (!afterTime.isEmpty() && !beforeTime.isEmpty()) {
            filter_case = IN_RANGE;
        }
        ArrayList<GhiChu> tempLs = new ArrayList<>();
        switch (filter_case) {
            case AFTER_TIME:
                for (int i = 0; i < ls.size(); i++) {
                    //Lấy ghi chú
                    GhiChu g = ls.get(i);
                    //Lấy giờ ghi chú
                    String td = g.getGio();
                    //tạo 2 đối tượng Time để so sánh
                    Time tafterTime = null, tNoteTime = null;
                    try {
                        //convert và lưu vào 2 đối tượng Time
                        tafterTime = new Time(formatter.parse(afterTime).getTime());
                        tNoteTime = new Time(formatter.parse(td).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //so sánh 2 thời gian nếu sau thì thêm vào mảng
                    if (tNoteTime.after(tafterTime)) {
                        tempLs.add(g);
                    }
                }
                break;
            case BEFORE_TIME: {
                for (int i = 0; i < ls.size(); i++) {
                    GhiChu g = ls.get(i);
                    String td = g.getGio();
                    Time tbeforeTime = null, tNoteTime = null;
                    try {
                        tbeforeTime = new Time(formatter.parse(beforeTime).getTime());
                        tNoteTime = new Time(formatter.parse(td).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //so sánh 2 thời gian nếu trược thì thêm vào mảng
                    if (tNoteTime.before(tbeforeTime)) {
                        tempLs.add(g);
                    }
                }
            }
            break;
            case IN_RANGE:
                for (int i = 0; i < ls.size(); i++) {
                    GhiChu g = ls.get(i);
                    String td = g.getGio();
                    Time tbeforeTime = null, tafterTime = null, tNoteTime = null;
                    try {
                        tbeforeTime = new Time(formatter.parse(beforeTime).getTime());
                        tafterTime = new Time(formatter.parse(afterTime).getTime());
                        tNoteTime = new Time(formatter.parse(td).getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //so sánh 2 thời gian, nếu thời gian của note nằm trong khoảng thời gian đã chọn thì thêm vào mảng tempLs
                    if (tNoteTime.before(tbeforeTime) && tNoteTime.after(tafterTime)) {
                        tempLs.add(g);
                    }
                }
        }
        return tempLs;
    }

    private void filterByTime(String afterTime, String beforeTime) {
        ArrayList<GhiChu> tempLs = FilterByTime(afterTime, beforeTime);
        //Xóa list cũ
        ls.clear();
        //Thêm list mới vào
        ls.addAll(tempLs);
        //Refresh listview
        ghiChuAdapter.notifyDataSetChanged();
    }

    public ArrayList<GhiChu> Filterbyday(String afterDate, String beforeDate) {
        final String AFTER_DATE = "AFTER_DATE";
        final String BEFORE_DATE = "BEFORE_DATE";
        final String IN_RANGE = "IN_RANGE";
        String filter_case = "";
        if (!afterDate.isEmpty() && beforeDate.isEmpty()) {
            filter_case = AFTER_DATE;
        } else if (afterDate.isEmpty() && !beforeDate.isEmpty()) {
            filter_case = BEFORE_DATE;
        } else if (!afterDate.isEmpty() && !beforeDate.isEmpty()) {
            filter_case = IN_RANGE;
        }

        ArrayList<GhiChu> tempLs = new ArrayList<>();
        switch (filter_case) {
            case AFTER_DATE:
                for (int i = 0; i < ls.size(); i++) {
                    //Lấy ghi chú
                    GhiChu g = ls.get(i);
                    //Lấy giờ ghi chú
                    String td = g.getNgaythang();
                    //tạo 2 đối tượng Time để so sánh
                    Date tafterDate = null, tNoteDate = null;

                    Log.e("Huy tự hủy 1", afterDate);
                    Log.e("Huy tự hủy 2", td);


                    try {
                        //convert và lưu vào 2 đối tượng Time
                        tafterDate = new SimpleDateFormat("dd/MM/yyyy").parse(afterDate);
                        tNoteDate = new SimpleDateFormat("dd/MM/yyyy").parse(td);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //so sánh 2 thời gian nếu sau thì thêm vào mảng
                    if (tNoteDate.after(tafterDate)) {
                        tempLs.add(g);
                    }
                }
                break;
            case BEFORE_DATE: {
                for (int i = 0; i < ls.size(); i++) {
                    GhiChu g = ls.get(i);
                    String td = g.getNgaythang();
                    Date tbeforeDate = null, tNoteDate = null;
                    try {
                        tbeforeDate = new SimpleDateFormat("dd/MM/yyyy").parse(beforeDate);
                        tNoteDate = new SimpleDateFormat("dd/MM/yyyy").parse(td);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //so sánh 2 thời gian nếu trược thì thêm vào mảng
                    if (tNoteDate.before(tbeforeDate)) {
                        tempLs.add(g);
                    }
                }
            }
            break;
            case IN_RANGE:
                for (int i = 0; i < ls.size(); i++) {
                    GhiChu g = ls.get(i);
                    String td = g.getNgaythang();
                    Date tbeforeDate = null, tafterDate = null, tNoteDate = null;
                    try {
                        tbeforeDate = new SimpleDateFormat("dd/MM/yyyy").parse(beforeDate);
                        tafterDate = new SimpleDateFormat("dd/MM/yyyy").parse(afterDate);
                        tNoteDate = new SimpleDateFormat("dd/MM/yyyy").parse(td);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //so sánh 2 thời gian, nếu thời gian của note nằm trong khoảng thời gian đã chọn thì thêm vào mảng tempLs
                    if (tNoteDate.before(tbeforeDate) && tNoteDate.after(tafterDate)) {
                        tempLs.add(g);
                    }
                }
        }
        return tempLs;
    }

    private void filterbyday(String afterDate, String beforeDate) {
        ArrayList<GhiChu> tempLs = Filterbyday(afterDate, beforeDate);
        //Xóa list cũ
        ls.clear();
        //Thêm list mới vào
        ls.addAll(tempLs);
        //Refresh listview
        ghiChuAdapter.notifyDataSetChanged();
    }

    //Tạo menu--------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                if(timkiem.getVisibility() == View.GONE)
                    timkiem.setVisibility(View.VISIBLE);
                else
                    timkiem.setVisibility(View.GONE);
                return true;

            /*case R.id.filter: {
                Intent i = new Intent(LichHenActivity.this, FilterActivity.class);
                i.setAction(FILTER);
                FResult.launch(i);
            }*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
