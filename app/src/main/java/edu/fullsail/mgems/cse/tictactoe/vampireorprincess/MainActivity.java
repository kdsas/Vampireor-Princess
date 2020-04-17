package edu.fullsail.mgems.cse.tictactoe.vampireorprincess;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {


    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    int[][] c;
    int i, j, k = 0;
    Button[][] b;
    TextView textView;
    AP ap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBoard();


        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


        }



        Button btn = findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationTrack = new LocationTrack(MainActivity.this);



                    double longitude = locationTrack.getLongitude();
                    double latitude = locationTrack.getLatitude();

                String someString = String.valueOf(locationTrack.getLongitude());
                char someChar = '8';
                int count = 0;
                for (int i = 0; i < someString.length(); i++) {
                    if (someString.charAt(i) == someChar) {
                        count++;
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("You've Earned 10 Points");
                        dialog.setMessage("Extra Game Points for Having the Number 8 Within Your Longitude\n" + count);
                        dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();

                    }
                }
                    Toast.makeText(getApplicationContext(), "Longitude:" + longitude + "\nLatitude:" + latitude, Toast.LENGTH_SHORT).show();
                }




        });}




    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();
        permissionsToRequest = findUnAskedPermissions(permissions);
        for (Object perm : wanted) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission((String) perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }



    private void setBoard() {
        ap = new AP();
        b = new Button[4][4];
        c = new int[4][4];


        textView = findViewById(R.id.dialogue);

        b[1][3] = findViewById(R.id.one);
        b[1][2] = findViewById(R.id.two);
        b[1][1] = findViewById(R.id.three);


        b[2][3] = findViewById(R.id.four);
        b[2][2] = findViewById(R.id.five);
        b[2][1] = findViewById(R.id.six);


        b[3][3] = findViewById(R.id.seven);
        b[3][2] = findViewById(R.id.eight);
        b[3][1] = findViewById(R.id.nine);

        for (i = 1; i <= 3; i++) {
            for (j = 1; j <= 3; j++)
                c[i][j] = 2;
        }



        // add the click listeners for each button
        for (i = 1; i <= 3; i++) {
            for (j = 1; j <= 3; j++) {
                b[i][j].setOnClickListener(new MyClickListener(i, j));
                if(!b[i][j].isEnabled()) {
                    b[i][j].setText(" ");
                    b[i][j].setEnabled(true);
                }
            }
        }
    }

    private class MyClickListener implements View.OnClickListener {
        int x;
        int y;

        MyClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            if (b[x][y].isEnabled()) {
                b[x][y].setEnabled(false);
                b[x][y].setText("O");
                c[x][y] = 0;
                textView.setText("");
                if (!checkBoard()) {
                    ap.takeTurn();
                }
            }
        }
    }

    public class AP {
        void takeTurn() {
            if(c[1][1]==2 &&
                    ((c[1][2]==0 && c[1][3]==0) ||
                            (c[2][2]==0 && c[3][3]==0) ||
                            (c[2][1]==0 && c[3][1]==0))) {
                markSquare(1,1);
            } else if (c[1][2]==2 &&
                    ((c[2][2]==0 && c[3][2]==0) ||
                            (c[1][1]==0 && c[1][3]==0))) {
                markSquare(1,2);
            } else if(c[1][3]==2 &&
                    ((c[1][1]==0 && c[1][2]==0) ||
                            (c[3][1]==0 && c[2][2]==0) ||
                            (c[2][3]==0 && c[3][3]==0))) {
                markSquare(1,3);
            } else if(c[2][1]==2 &&
                    ((c[2][2]==0 && c[2][3]==0) ||
                            (c[1][1]==0 && c[3][1]==0))){
                markSquare(2,1);
            } else if(c[2][2]==2 &&
                    ((c[1][1]==0 && c[3][3]==0) ||
                            (c[1][2]==0 && c[3][2]==0) ||
                            (c[3][1]==0 && c[1][3]==0) ||
                            (c[2][1]==0 && c[2][3]==0))) {
                markSquare(2,2);
            } else if(c[2][3]==2 &&
                    ((c[2][1]==0 && c[2][2]==0) ||
                            (c[1][3]==0 && c[3][3]==0))) {
                markSquare(2,3);
            } else if(c[3][1]==2 &&
                    ((c[1][1]==0 && c[2][1]==0) ||
                            (c[3][2]==0 && c[3][3]==0) ||
                            (c[2][2]==0 && c[1][3]==0))){
                markSquare(3,1);
            } else if(c[3][2]==2 &&
                    ((c[1][2]==0 && c[2][2]==0) ||
                            (c[3][1]==0 && c[3][3]==0))) {
                markSquare(3,2);
            }else if( c[3][3]==2 &&
                    ((c[1][1]==0 && c[2][2]==0) ||
                            (c[1][3]==0 && c[2][3]==0) ||
                            (c[3][1]==0 && c[3][2]==0))) {
                markSquare(3,3);
            } else {
                Random rand = new Random();

                int a = rand.nextInt(4);
                int b = rand.nextInt(4);
                while(a==0 || b==0 || c[a][b]!=2) {
                    a = rand.nextInt(4);
                    b = rand.nextInt(4);
                }
                markSquare(a,b);
            }
        }


        private void markSquare(int x, int y) {
            b[x][y].setEnabled(false);
            b[x][y].setText("X");
            c[x][y] = 1;
            checkBoard();
        }
    }

    private void markSquare(int x, int y) {
        b[x][y].setEnabled(false);
        b[x][y].setText("X");
        c[x][y] = 1;
        checkBoard();
    }

    // check the board to see if someone has won
    private boolean checkBoard() {
        boolean gameOver = false;
        if ((c[1][1] == 0 && c[2][2] == 0 && c[3][3] == 0)
                || (c[1][3] == 0 && c[2][2] == 0 && c[3][1] == 0)
                || (c[1][2] == 0 && c[2][2] == 0 && c[3][2] == 0)
                || (c[1][3] == 0 && c[2][3] == 0 && c[3][3] == 0)
                || (c[1][1] == 0 && c[1][2] == 0 && c[1][3] == 0)
                || (c[2][1] == 0 && c[2][2] == 0 && c[2][3] == 0)
                || (c[3][1] == 0 && c[3][2] == 0 && c[3][3] == 0)
                || (c[1][1] == 0 && c[2][1] == 0 && c[3][1] == 0)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Won");
            dialog.setMessage("Game over. Barbie wins!");
            dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            dialog.show();




            gameOver = true;
        } else if ((c[1][1] == 1 && c[2][2] == 1 && c[3][3] == 1)
                || (c[1][3] == 1 && c[2][2] == 1 && c[3][1] == 1)
                || (c[1][2] == 1 && c[2][2] == 1 && c[3][2] == 1)
                || (c[1][3] == 1 && c[2][3] == 1 && c[3][3] == 1)
                || (c[1][1] == 1 && c[1][2] == 1 && c[1][3] == 1)
                || (c[2][1] == 1 && c[2][2] == 1 && c[2][3] == 1)
                || (c[3][1] == 1 && c[3][2] == 1 && c[3][3] == 1)
                || (c[1][1] == 1 && c[2][1] == 1 && c[3][1] == 1)) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("Lost");
            dialog.setMessage("Game over. Vampire lost!");
            dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            dialog.show();



            gameOver = true;
        } else {
            boolean empty = false;
            for(i=1; i<=3; i++) {
                for(j=1; j<=3; j++) {
                    if(c[i][j]==2) {
                        empty = true;
                        break;
                    }
                }
            }
            if(!empty) {
                gameOver = true;

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("Draw");
                dialog.setMessage("It's a draw!");
                dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        }
        return gameOver;
    }






}