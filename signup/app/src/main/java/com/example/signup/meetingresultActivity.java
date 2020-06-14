package com.example.signup;

        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Toast;

        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class meetingresultActivity  extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetingresult);
    }

    public void onClickHandler(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("이 내용에 동의하시겠습니까?").setMessage("동의하지 않으면 매칭을 할 수 없습니다.");

        builder.setPositiveButton("동의", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(getApplicationContext(),"동의",Toast.LENGTH_SHORT).show();
                databaseReference.child("test").child("agree").child("owner_agree").setValue("t");
                // 견주가 동의를 눌렀을 경우
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Toast.makeText(getApplicationContext(), "취소", Toast.LENGTH_SHORT).show();

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
