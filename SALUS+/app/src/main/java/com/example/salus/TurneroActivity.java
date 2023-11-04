package com.example.salus;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.salus.adaptador.CalendarAdapter;

import com.example.salus.entidad.Usuario;
import com.example.salus.negocio.IUsuarioNeg;
import com.example.salus.negocioImpl.UsuarioNegImpl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TurneroActivity extends AppCompatActivity
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private Button reservar;
    private TextView txtFecha;
    private Button btnFecha;
    private TextView txtHora;
    private Button btnHora;
    private Spinner spinner;
    IUsuarioNeg iUsuarioNeg;
    Context context;

    //@RequiresApi(api = Build.VERSION_CODES.O)
    //@Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_turnero);
        reservar = findViewById(R.id.btnReservarCita);
        txtFecha = findViewById(R.id.txt_fecha);
        btnFecha = findViewById(R.id.btn_fecha);
        txtHora = findViewById(R.id.txt_hora);
        btnHora = findViewById(R.id.btn_hora);
        spinner = findViewById(R.id.spinner);
        context = getApplicationContext();
        List<Usuario> listaProf = llenarProfesionales();
        ArrayAdapter<Usuario> arrayAdapter = new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaProf);
        spinner.setAdapter(arrayAdapter);
        //initWidgets();
        //selectedDate = LocalDate.now();
        //setMonthView();
    }
    private List<Usuario> llenarProfesionales(){
        List listaProf = new ArrayList<>();
        iUsuarioNeg = new UsuarioNegImpl();
        //Usuario lista = new Usuario();
        listaProf = iUsuarioNeg.listarTodos(context);
       //listaProf.add(lista);
        return listaProf;
    }

    public void abrirCalendario(View view) {
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(TurneroActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String fecha = i2 + "/" + i1 + "/" + i;
                txtFecha.setText(fecha);
            }
        },anio, mes, dia);
        dpd.show();
    }

    public void abrirRejoj(View view){
        Calendar time = Calendar.getInstance();
        int hora = time.get(Calendar.HOUR_OF_DAY);
        int minutos = time.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(TurneroActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                txtHora.setText(i + ":" + i1);
            }
        },0, 0, false);
        tpd.show();
    }

    public void irTurnos(View view){
        Intent intent = new Intent(this, Turnos.class);
        startActivity(intent);
    }

    private void initWidgets()
    {
        //calendarRecyclerView = findViewById(R.id.calendarRecuclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        //CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);

        calendarRecyclerView.setLayoutManager(layoutManager);
        //calendarRecyclerView.setAdapter(calendarAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }

        return daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonth(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonth(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();

    }

    //@Override
    public void onItemClick(int position, String dayText)
    {
        if (dayText.equals(""))
        {
           int i = 1; // String message = "Fecha Seleccionada " + dayText + " " + monthYearFromDate(selectedDate);
           //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    //@Override
    public void onItemClick(int position, LocalDate date) {

    }
}