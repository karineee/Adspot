package com.example.adspot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ConsumerHomeActivity extends AppCompatActivity {


    public static MobileServiceClient mClient;
    private static String globalReply = "0";


    //Below is from https://www.youtube.com/watch?v=WJBs0zKGqH0
    public Connection con;
    public TextView message;

    public static String master = "";

    /*
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            run = (Button) findViewById(R.id.button);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);

            run.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckLogin checkLogin = new CheckLogin();
                    checkLogin.execute("");
                }
            });
        }

        public class CheckLogin extends AsyncTask<String, String, String> {
            String z = "";
            String name1 = "";
            Boolean isSuccess = false;

            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);

            }

        }*/
    String z = "";
    String testmaster = "";
    String name = "";
    String name1 = "";
    String name2 = "";
    String name3 = "";
    String name4 = "";
    String name5 = "";
    String name6 = "";
    Boolean isSuccess = false;

    /*
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Toast.makeTest(MainActivity.this, r, Toast.LENGTH_LONG).show();

        }
    */
    private static void getReply(String rep) {
        globalReply = rep;
        Log.d("GLOBALREPLY: ", globalReply); //proves that the username is found
    }

    public static AsyncTask<Void, Void, String> runAsyncTask2(AsyncTask<Void, Void, String> task) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            return task.execute();
        }
    }
/*
    protected String doInBackground(String... params) {
        try {
            con = connectionClass();
            if (con == null) {
                z = "Check your Internet Access!";

            } else {
                //Change below query according to your database
                String query = "select * from RecommendationTable";
                Statement stmt = con.createStatement();



                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    name1 = rs.getString("Link1"); // Name of Column
                    name2 = rs.getString("Rec1"); // Name of Column

                    name3 = rs.getString("Link2"); // Name of Column
                    name4 = rs.getString("Rec2"); // Name of Column


                    name5 = rs.getString("Link3"); // Name of Column
                    name6 = rs.getString("Rec3"); // Name of Column


                    z = "query successful";




                    if (rs.next()) {

                        name1 = rs.getString("Link1"); // Name of Column



                    }
                    isSuccess = true;
                    if (isSuccess) {
                        message = (TextView) findViewById(R.id.textView2);
                        message.setText(name1);
                    }
                    con.close();

                } else {
                    z = "Invalid query";
                    isSuccess = false;
                }
            }
        } catch (Exception ex) {
            isSuccess = false;
            z = ex.getMessage();

            Log.d("sql error", z);
        }
        return z;

    }
*/




    @SuppressLint("NewApi")
    public Connection connectionClass() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://mysqlserver1271996.database.windows.net:1433;DatabaseName=mySampleDatabase;user=azureuser@mysqlserver1271996;password=AdspotSJSU2019;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return connection;
    }


    public static void getTableVal() {
        final MobileServiceTable<DummyTable> mTable = mClient.getTable(DummyTable.class);

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            List<DummyTable> res = null;
            DummyTable obj = new DummyTable();

            @Override
            protected String doInBackground(Void... params) {
                String Result = "0";

                Log.d("myTag", "Hello future Karine! ");
                try {
                    res = mTable
                            .select("username")
                            .execute()
                            .get();

                    obj = res.get(0);
                    int i = 0;
                    while (obj != null){
                        obj = res.get(i);
                        Result = obj.getUsername();
                        Log.d("TASKREPLY0: ", Result);

                        Log.d("myTag", "Hello future Karine! ");
                        if (Result.equals("Testman1")) {
                            Log.d("myTag", "Hello future Karine! ");
                            break;
                        }
                        i = i + 1;


                    }


                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return Result;

            }

            @Override
            protected void onPostExecute(String Result){
                getReply(Result);
            }
        };
        runAsyncTask2(task);

    }









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_home);
        try {
            con = connectionClass();
            if (con == null) {
                z = "Check your Internet Access!";

            } else {
                //Change below query according to your database
                String query = "select * from RecommendationTable";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);



//change
                //test search by id from









                while(rs.next()){
                    name =  rs.getString("Id"); // Name of Column
                    Log.d("myTag", "Id: " + name);


                    if( name.equals("101"))
                    {
                        Log.d("myTag", "Id found: " + name);


                        name1 = rs.getString("Rec1"); // Name of Column
                        name2 = rs.getString("Rec2"); // Name of Column
                        name3 = rs.getString("Rec3"); // Name of Column
                        name4 = rs.getString("Link1"); // Name of Column
                        name5 = rs.getString("Link2"); // Name of Column
                        name6 = rs.getString("Link3"); // Name of Column

                        Log.d("myTag", "Product1: " + name1);
                        Log.d("myTag", "Link1: " + name4);
                        Log.d("myTag", "Product2: " + name2);
                        Log.d("myTag", "Link2: " + name5);
                        Log.d("myTag", "Product3: " + name3);
                        Log.d("myTag", "Link3: " + name6);

                    }
                    rs.next();
                }


                if (rs.next()) {

                  /*
                    name1 = rs.getString("Rec1"); // Name of Column
                    name2 = rs.getString("Rec2"); // Name of Column
                    name3 = rs.getString("Rec3"); // Name of Column
                    name4 = rs.getString("Link1"); // Name of Column
                    name5 = rs.getString("Link2"); // Name of Column
                    name6 = rs.getString("Link3"); // Name of Column */

                    /* OUTPUTS SECOND PRODUCT IN LIST... rs.next() goes down a row!
                    if (rs.next()){
                        name1 = rs.getString("Rec1");
                        Log.d("myTag", "Hello future Karine! " + name1);

                    }*/

                    z = "query successful";

                    isSuccess = true;
                    if (isSuccess) {
                        message = (TextView) findViewById(R.id.textView2);
                        message.setText(name1);
                    }
                    con.close();

                } else {
                    z = "Invalid query";
                    isSuccess = false;
                }
            }
        } catch (Exception ex) {
            isSuccess = false;
            z = ex.getMessage();

            Log.d("sql error", z);
        }
        // return z;



        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        String username = message.substring(0, message.indexOf('.'));
        String password = message.substring((message.indexOf('.') + 1));


//changed output

        // String master = name1;
        // String master = z;
        // String master = "welcome";
        // String master = message;










        // Capture the layout's TextView and set the string as its text from the res/layout/activity_consumer_home.xml



        ImageView ListImg2 = findViewById(R.id.ListImg2);
        ImageView ListImg3 = findViewById(R.id.ListImg3);
        ImageView ListImg4 = findViewById(R.id.ListImg4);
        ImageView ListImg5 = findViewById(R.id.ListImg5);




        //Welcome user message

        master = "Welcome, " + username + "!";

        TextView textView = findViewById(R.id.textView2);
        textView.setText(master);


        //Displays product 1 image link
        ImageView ListPic1 = findViewById(R.id.ListImg1);
        Picasso.get().load(name4).into(ListPic1);

        //Displays product 1 name
        master = name1;
        TextView textView2 = findViewById(R.id.ListTxt1);
        textView2.setText(master);

        //Displays product 2 image
        ImageView ListPic2 = findViewById(R.id.ListImg2);
        Picasso.get().load(name5).into(ListPic2);


        //Displays produce 2 name
        master = name2;
        TextView textView3 = findViewById(R.id.ListTxt2);
        textView3.setText(master);


        //Displays product 3 image
        ImageView ListPic3 = findViewById(R.id.ListImg3);
        Picasso.get().load(name6).into(ListPic3);

        //Displays product 3 name
        master = name3;
        TextView textView4 = findViewById(R.id.ListTxt3);
        textView4.setText(master);



        if (username.equals("MrHappy")) {

            ImageView product1 = findViewById(R.id.ListImg1);
            product1.setImageResource(R.drawable.happy);

            ImageView product2 = findViewById(R.id.ListImg2);
            product2.setImageResource(R.drawable.happy);

            ImageView product3 = findViewById(R.id.ListImg3);
            product3.setImageResource(R.drawable.happy);

            ImageView product4 = findViewById(R.id.ListImg4);
            product4.setImageResource(R.drawable.happy);

            ImageView product5 = findViewById(R.id.ListImg5);
            product5.setImageResource(R.drawable.happy);
        } else if (username.equals("MrSad")) {
            ImageView product1 = findViewById(R.id.ListImg1);
            product1.setImageResource(R.drawable.sad);

            ImageView product2 = findViewById(R.id.ListImg2);
            product2.setImageResource(R.drawable.sad);

            ImageView product3 = findViewById(R.id.ListImg3);
            product3.setImageResource(R.drawable.sad);
        }




    }


}

