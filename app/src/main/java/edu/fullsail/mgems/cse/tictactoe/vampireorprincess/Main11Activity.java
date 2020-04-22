package edu.fullsail.mgems.cse.tictactoe.vampireorprincess;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaledrone.lib.Listener;
import com.scaledrone.lib.Room;
import com.scaledrone.lib.RoomListener;
import com.scaledrone.lib.Scaledrone;

import java.util.Random;

import static java.lang.String.valueOf;

public class Main11Activity extends AppCompatActivity implements RoomListener {


    private String channelID = "";
    private String roomName = "observable-room";
    private EditText editText;
    private Scaledrone scaledrone;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
   public  int score = 0;
    TextView textView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main11);

        editText = findViewById(R.id.editText);
        Intent intent = getIntent();
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);
        messageAdapter = new MessageAdapter(this);
        messagesView = findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);

        MemberData data = new MemberData(getRandomName(), getRandomColor());
        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText("Score:"+score);
        scaledrone = new Scaledrone(channelID, data);
        scaledrone.connect(new Listener() {
            @Override
            public void onOpen() {
                System.out.println("Scaledrone connection open");
                scaledrone.subscribe(roomName, Main11Activity.this);
            }

            @Override
            public void onOpenFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onFailure(Exception ex) {
                System.err.println(ex);
            }

            @Override
            public void onClosed(String reason) {
                System.err.println(reason);
            }
        });
    }


    public void sendMessage(View view) {
        textView3.setText("Score:"+score);
        String u = String.valueOf("Score:"+score);
        scaledrone.publish(roomName, u);
if(textView3.getText().equals("Near Object")){score++;}
        String message = editText.getText().toString();
        if (message.length() > 0) {
            scaledrone.publish(roomName, message);
            editText.getText().clear();
            String someString = message;
            char someChar = 'o';
            int count = 0;
            for (int i = 0; i < someString.length(); i++) {
                if (someString.charAt(i) == someChar) {
                    count++;
                    score--;
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Main11Activity.this);
                    dialog.setTitle("You've Lost Points");
                    dialog.setMessage("Lost Game Points for Having the Letter o Within Your Text\n"  + score);
                    dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener( ) {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss( );
                        }
                    });
                    dialog.show( );}
                }

        }

    }

    @Override
    public void onOpen(Room room) {
        System.out.println("Conneted to room");
    }

    @Override
    public void onOpenFailure(Room room, Exception ex) {
        System.err.println(ex);
    }

    @Override
    public void onMessage(Room room, com.scaledrone.lib.Message receivedMessage) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            final MemberData data = mapper.treeToValue(receivedMessage.getMember().getClientData(), MemberData.class);
            boolean belongsToCurrentUser = receivedMessage.getClientID().equals(scaledrone.getClientID());
            final Message message = new Message(receivedMessage.getData().asText(), data, belongsToCurrentUser);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    messageAdapter.add(message);
                    messagesView.setSelection(messagesView.getCount() - 1);
                }
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    private String getRandomName() {
        Intent intent = getIntent( );
        String text = intent.getStringExtra(Intent.EXTRA_TEXT);
        String[] adjs = {"vampire", "princess"};
        if(adjs.equals("princess")){
           score++;
        }else{
            score--;
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(Main11Activity.this);
        dialog.setTitle("You've Earned or Lost a Point");
        dialog.setMessage("Extra Game Points or Lost Game Points \n" + score);
        dialog.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        dialog.show();
        String[] nouns = {valueOf(text)};
        if(score >100 ){
            AlertDialog.Builder dialogo = new AlertDialog.Builder(Main11Activity.this);
            dialogo.setTitle(nouns+ "Has Won the Game");
            dialogo.setMessage("Winner \n" + score);
            dialogo.setPositiveButton(" OK ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            dialogo.show();
        }

        return (adjs[(int) Math.floor(Math.random( ) * adjs.length)] +
                "_" +
                nouns[(int) Math.floor(Math.random( ) * nouns.length)]);

    }




    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while(sb.length() < 7){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }
}

class MemberData {
    private String name;
    private String color;

    public MemberData(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public MemberData() {
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "MemberData{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
