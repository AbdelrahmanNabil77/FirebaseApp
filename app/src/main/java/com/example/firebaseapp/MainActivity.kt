package com.example.firebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebaseapp.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val database=Firebase.database
        val ref=database.getReference("name")
        var output="EMPTY"
        // Read from the database
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue()
               output=value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                output=error.message
            }
        })
        binding.saveBtn.setOnClickListener {
            if (!binding.inputEditText.text.isNullOrEmpty()){
                ref.setValue(binding.inputEditText.text.toString())
            }
        }
        binding.getBtn.setOnClickListener {
            binding.outputTextView.text=output
        }

    }
}