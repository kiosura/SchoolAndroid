package com.example.schoolandroid.dialogs


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.schoolandroid.R

class PushDialog : DialogFragment(), OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.push_layout, null)

        view.findViewById<Button>(R.id.closepush).setOnClickListener(this);

        return view
    }

    override fun onClick(v: View?) {
        dialog?.cancel()
    }
}
