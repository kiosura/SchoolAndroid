package com.example.schoolandroid.dialogs

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.schoolandroid.R
import com.example.schoolandroid.api.StorageViewModel
import com.example.schoolandroid.databinding.UpdateUserDialogBinding
import com.example.schoolandroid.storage.Storage

class UpdateUserDialog : DialogFragment() {

    private lateinit var storageViewModel : StorageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setStyle(STYLE_NORMAL, R.style.custom_dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.update_user_dialog, container, false)

        storageViewModel = ViewModelProvider(this).get(StorageViewModel::class.java)

        val binding = UpdateUserDialogBinding.bind(view)
        fun bind() = with(binding) {
            Storage.getUser().observe(viewLifecycleOwner) { user ->
                userEmailUpdate.setText(if (user.email != "") user.email else "")
                userPhoneUpdate.setText(if (user.phone_number != "") user.phone_number else "")
                userNameUpdate.setText(if (user.name != "") user.name else "")
                userSurnameUpdate.setText(if (user.surname != "") user.surname else "")
            }
            updateButton.setOnClickListener {
                storageViewModel.postUpdateUser(
                    Storage.getUser().value!!.id!!,
                    userNameUpdate.text.toString(),
                    userSurnameUpdate.text.toString(),
                    userEmailUpdate.text.toString(),
                    userPhoneUpdate.text.toString())
                dialog?.cancel()
            }
        }

        bind()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}