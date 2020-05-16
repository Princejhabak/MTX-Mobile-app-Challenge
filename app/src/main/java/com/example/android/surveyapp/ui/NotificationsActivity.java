package com.example.android.surveyapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.android.surveyapp.MainActivity;
import com.example.android.surveyapp.R;
import com.example.android.surveyapp.adapters.NotificationAdapter;
import com.example.android.surveyapp.adapters.SurveyHistoryAdapter;
import com.example.android.surveyapp.models.Notification;
import com.example.android.surveyapp.models.Survey;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Notification> itemList;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private Spinner spinner;

    private String spinnerText = "Unread";

    private String[] spinnerItems ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView  = findViewById(R.id.notification_recycler_view);
        recyclerView.hasFixedSize();

        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);

        spinner = findViewById(R.id.notifications_spinner);
        spinnerItems = getResources().getStringArray(R.array.notification_status);

        //Creating the ArrayAdapter instance having the list
        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(adapter);


        firebaseAuth = FirebaseAuth.getInstance();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = firebaseDatabase.getReference("SurveyApp");

        generateItem();

        /** ItemTouchHelper will identify gestures(swipes) */
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    spinnerText = item.toString();
                    getDetailsFormDatabase();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    @Override
    public boolean onNavigateUp() {
        finish();
        return super.onNavigateUp();
    }

    private void generateItem() {
        itemList = new ArrayList<>();

        adapter = new NotificationAdapter(this, itemList);
        recyclerView.setAdapter(adapter);

        getDetailsFormDatabase();

    }

    Notification readItem = null;

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();

            switch (direction){

                /** Left to right swipe */
                case ItemTouchHelper.LEFT:
                    readItem = itemList.get(position);
                    itemList.remove(position);
                    adapter.notifyItemRemoved(position);

                    String date1 = calculateDate(readItem.getNotificationDateTime());

                    mDatabaseReference.child(firebaseAuth.getCurrentUser().getPhoneNumber()).child("Notifications").child(date1).removeValue();

                    Toast.makeText(NotificationsActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    break;

                /** Right to left swipe */
                case ItemTouchHelper.RIGHT:
                    readItem = itemList.get(position);
                    readItem.setNotificationRead(true);

                    itemList.remove(position);
                    adapter.notifyItemRemoved(position);

//                    itemList.add(position, readItem);
//                    adapter.notifyItemInserted(position);

                    String date2 = calculateDate(readItem.getNotificationDateTime());

                    mDatabaseReference.child(firebaseAuth.getCurrentUser().getPhoneNumber()).child("Notifications").child(date2).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            dataSnapshot.getRef().child("notificationRead").setValue(true);

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("Error", databaseError.getMessage());
                        }
                    });

                    Toast.makeText(NotificationsActivity.this, "Marked as read", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

        /** Add drawable images and background color for left and right swipes */
        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(NotificationsActivity.this, R.color.notification_delete_background))
                    .addSwipeRightActionIcon(R.drawable.ic_marked_read)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(NotificationsActivity.this, R.color.seek_bar_progress))
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };

    /** Back button */
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void getDetailsFormDatabase(){

        mDatabaseReference.child(firebaseAuth.getCurrentUser().getPhoneNumber()).child("Notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                itemList.clear();

                for(DataSnapshot surveySnapshot:dataSnapshot.getChildren()){
                    Notification notification = surveySnapshot.getValue(Notification.class);

                    if(spinnerText.equals("Read")){
                        if(notification.isNotificationRead())
                            itemList.add(notification);
                    }
                    else{
                        if(!notification.isNotificationRead())
                            itemList.add(notification);
                    }


                }

                /** notify adapter for changes */
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /** Calculate date from given unix timestamp */
    public String calculateDate(String timestamp){
        long unixSeconds = Long.parseLong(timestamp);
        Date date = new java.util.Date(unixSeconds*1000L);

        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMdd");

        return sdf.format(date);

    }

}
