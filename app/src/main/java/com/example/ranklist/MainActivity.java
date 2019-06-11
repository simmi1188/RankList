package com.example.ranklist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;




public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ArrayList<Student> studentlist;
    private Menu menu;
    private boolean nameAsce, marksAsce;
    private RadioButton radio_button1;
    private RadioButton radio_button2;
    private RadioButton radio_button3;
    private boolean filter;
    private Student maximum;
    private Student minimum;
    private TextView textView;
    private CardView cardView;
    ArrayList<Student> newList;
    private int minIndex = -1;
    private int maxIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nameAsce = true;
        marksAsce = false;
        textView = (TextView)findViewById(R.id.textview);
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        studentlist = new ArrayList<>();
        newList = new ArrayList<>();
        cardView = (CardView) findViewById(R.id.card);
        filter = true;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String[] namearray = getResources().getStringArray(R.array.string_array);
        int[] marks = getResources().getIntArray(R.array.number);

        for (int i = 0; i < namearray.length; i++) {
            String name = namearray[i];
            int mark = marks[i];
            Student student = new Student();
            student.setName(name);
            student.setMarks(mark);
            studentlist.add(student);
        }
        sortStudent(false, false);

        for (Student student : studentlist) {
            student.setRank(studentlist.indexOf(student) + 1);
        }

                myAdapter = new MyAdapter(this);
                myAdapter.getStudList1().addAll(studentlist);
                recyclerView.setAdapter(myAdapter);
                myAdapter.updateList();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main,menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);

        final android.support.v7.widget.SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.background_light));
        searchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));
        searchAutoComplete.setHint("Search");
        searchView.setOnQueryTextListener(this);
        ImageView searchClose = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setImageResource(R.drawable.close);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                   menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(false);
                menu.getItem(3).setVisible(false);
                menu.getItem(4).setVisible(false);
                menu.getItem(5).setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                menu.getItem(0).setVisible(true);
                menu.getItem(1).setVisible(true);
                menu.getItem(2).setVisible(true);
                menu.getItem(3).setVisible(true);
                menu.getItem(4).setVisible(true);
                menu.getItem(5).setVisible(true);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id){
            case R.id.sortOnName:
                sortStudent(true,nameAsce);
                nameAsce=!nameAsce;
                item.setIcon(nameAsce ? R.drawable.nameasec : R.drawable.namedsec);
                myAdapter.getStudList1().clear();
                myAdapter.getStudList1().addAll(studentlist);
                myAdapter.updateList();
                    break;

            case R.id.sortOnMarks:
                sortStudent(false,marksAsce);
                myAdapter.getStudList1().clear();
                myAdapter.getStudList1().addAll(studentlist);
                myAdapter.updateList();
                marksAsce=!marksAsce;
                item.setIcon(marksAsce ? R.drawable.markasec : R.drawable.markdsec);
                break;

            case R.id.filter :
                if (filter) {
                    final View buttonEntryView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
                    radio_button1 = (RadioButton) buttonEntryView.findViewById(R.id.Button1);
                    radio_button2 = (RadioButton) buttonEntryView.findViewById(R.id.Button2);
                    radio_button3 = (RadioButton) buttonEntryView.findViewById(R.id.Button3);

                    final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setView(buttonEntryView)
                            .setTitle("Students Result")
                            .create();
                    alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(final DialogInterface dialog) {
                            radio_button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sortStudent(false, false);
                                    myAdapter.getStudList1().clear();
                                    myAdapter.getStudList1().addAll(studentlist);
                                    myAdapter.updateList();
                                    dialog.dismiss();
                                }
                            });

                            radio_button2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ArrayList<Student> studentPass = new ArrayList<>();
                                   for (int i = 0; i < studentlist.size(); i++) {
                                        if (studentlist.get(i).getMarks() >= 45) {
                                            studentPass.add(studentlist.get(i));
                                        }
                                    }
                                    myAdapter.getStudList1().clear();
                                   myAdapter.getStudList1().addAll(studentPass);
                                    myAdapter.updateList();
                                    dialog.dismiss();
                                }
                            });

                            radio_button3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ArrayList<Student> studentFail = new ArrayList<>();
                                    for (int i = 0; i < studentlist.size(); i++) {
                                        if (studentlist.get(i).getMarks() < 45) {
                                            studentFail.add(studentlist.get(i));
                                        }
                                    }
                                    myAdapter.getStudList1().clear();
                                    myAdapter.getStudList1().addAll(studentFail);
                                    myAdapter.updateList();
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                    alertDialog.show();
                    filter=!filter;
                    item.setIcon(filter ? R.drawable.filter : R.drawable.cross);
                    menu.getItem(0).setVisible(false);
                    menu.getItem(1).setVisible(false);
                    menu.getItem(2).setVisible(false);
                    menu.getItem(4).setVisible(false);
                    menu.getItem(5).setVisible(false);
                }

                else if (!filter)
                {
                    sortStudent(false,false);
                    myAdapter.getStudList1().clear();
                    myAdapter.getStudList1().addAll(studentlist);
                    myAdapter.updateList();
                    filter=!filter;
                    item.setIcon(filter ? R.drawable.filter : R.drawable.cross);
                    menu.getItem(0).setVisible(true);
                    menu.getItem(1).setVisible(true);
                    menu.getItem(2).setVisible(true);
                    menu.getItem(4).setVisible(true);
                    menu.getItem(5).setVisible(true);
                }
                break;

            case R.id.maximum :
                sortStudent(false,false);
                myAdapter.getStudList1().clear();
                myAdapter.getStudList1().add(studentlist.get(0));
                myAdapter.updateList();
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(false);
                menu.getItem(4).setVisible(false);
                menu.getItem(5).setVisible(false);
                filter =!filter;
                menu.getItem(3).setIcon(filter ? R.drawable.filter : R.drawable.cross);
                break;

            case R.id.minimum :
                sortStudent(false,true);
                myAdapter.getStudList1().clear();
                myAdapter.getStudList1().add(studentlist.get(0));
                myAdapter.updateList();
                menu.getItem(0).setVisible(false);
                menu.getItem(1).setVisible(false);
                menu.getItem(2).setVisible(false);
                menu.getItem(4).setVisible(false);
                menu.getItem(5).setVisible(false);
                filter= !filter;
                menu.getItem(3).setIcon(filter ? R.drawable.filter : R.drawable.cross);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String userInput = newText.toLowerCase();
        myAdapter.getStudList1().clear();

        for (Student string : studentlist )
        {
            if (string.getName().toLowerCase().contains(userInput))
            myAdapter.getStudList1().add(string);
        }

        if (myAdapter.getStudList1().isEmpty())
        {
            recyclerView.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
        else
        {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);
        }
        myAdapter.updateList();
        return true;
    }

    private void sortStudent(final boolean sortOnName, final boolean Ascending){
            Collections.sort(studentlist, new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    int result = 0;
                    if(sortOnName){
                        if (Ascending)
                            result=o1.getName().compareTo(o2.getName());
                        else
                            result=o2.getName().compareTo(o1.getName());

                    }
                    else
                        {
                            int marks1 = Ascending  ? o1.getMarks() : o2.getMarks();
                           int marks2 = Ascending ?  o2.getMarks(): o1.getMarks();
                            if (marks1 > marks2)
                         result = 1;
                            else if (marks1 < marks2)
                        result = -1;
                    }
                    return result;
                }
            });

    }


}