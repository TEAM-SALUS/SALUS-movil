package com.example.salus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.salus.entidad.Pago;

import java.util.ArrayList;
import java.util.Calendar;

public class Turnero_Medicos extends AppCompatActivity {

    private TextView spinnerDoctor;
    private TextView tvSelectDoctor;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnero_medicos);

        spinnerDoctor = findViewById(R.id.spinnerDoctor);
        tvSelectDoctor = findViewById(R.id.tvSelectDoctor);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Configurar Spinner con una lista de doctores
        /*ArrayList<String> doctorList = new ArrayList<>();
        doctorList.add("Dr. Smith");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, doctorList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctor.setAdapter(adapter);*/

        // Comente lo de arriba porque ese codigo creaba el spinner, el cual ya no es mas un spinner
        // sino que es un textView que seria donde se ve la especialidad ahora

        // Este es el bundle que se trae los datos que mando desde la activity ProfesionalActivity.java
        // Te mando el nombre completo del medico y la especialidad
        // Le sumo tambien la ID del medico y la ID de la especialidad por si te lo piden
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String medico_incoming = extras.get("Medico").toString();
        String especialidad_incoming = extras.get("Especialidad").toString();

        tvSelectDoctor.setText(medico_incoming);
        spinnerDoctor.setText(especialidad_incoming);

        String medicoId = extras.get("MedicoID").toString();
        String especialidadId = extras.get("EspecialidadID").toString();
        Log.d("Medico ID:" , medicoId);
        Log.d("Especialidad ID:" , especialidadId);

        /*
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intento = new Intent(Turnero_Medicos.this, Pago.class);
                intento.putExtra("Medico",spinnerDoctor.getText());
                intento.putExtra("MedicoID",medicoId);
                intento.putExtra("EspecialidadID",especialidadId);

                //String selectedDoctor = spinnerDoctor.getSelectedItem().toString();
                String selectedDoctor = "asd";
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                intento.putExtra("Fecha",year+"/"+month+"/"+day);
                intento.putExtra("Hora",hour+":"+minute);



                String appointmentDetails = "Doctor: " + selectedDoctor + "\n" +
                        "Date: " + day + "/" + (month + 1) + "/" + year + "\n" +
                        "Time: " + String.format("%02d:%02d", hour, minute);

                Toast.makeText(Turnero_Medicos.this, appointmentDetails, Toast.LENGTH_LONG).show();

                startActivity(Intent.createChooser(intento,"Compartir en").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            }
        });
         */
    }


}
