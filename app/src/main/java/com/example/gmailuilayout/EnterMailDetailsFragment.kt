package com.example.gmailuilayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.example.gmailuilayout.databinding.FragmentEnterMailDetailsBinding
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime

class EnterMailDetailsFragment : Fragment() {
    private val emailViewModel: EmailsListViewModel by activityViewModels()
    private lateinit var binding: FragmentEnterMailDetailsBinding

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.enter_mail_details_app_bar_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.sendActionMenu -> {

                    if (validateEmailsItem()) {
                        val newEmailsDetails = EmailItem(
                            emailId = emailViewModel.getEmailsCount() + 1,
                            senderImage = emailViewModel.getRandomUserImage(),
                            senderName = binding.senderNameEditText.text.toString(),
                            emailDateTime = LocalDateTime.now(),
                            emailSubject = binding.subjectEditText.text.toString(),
                            emailMessage = binding.messageEditText.text.toString(),
                            starMarked = false
                        )
                        emailViewModel.addNewEmails(newEmailsDetails)
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }

                    return true
                }

                R.id.attachmentActionMenu -> {

                    binding.root.showSnackBarWithAction("attachment button clicked")

                    return true
                }

                R.id.discardActionMenu -> {
                    binding.root.showSnackBarWithAction("discard button click")
                    return true
                }

                R.id.settingActionMenu -> {
                    binding.root.showSnackBarWithAction("setting button click")
                    return true
                }

                R.id.saveDraftActionMenu -> {
                    binding.root.showSnackBarWithAction("save draft button click")

                    return true
                }

                else -> {
                    return false
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEnterMailDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setAppBarAndBottomNavVisible(false)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(
            menuProvider,
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        binding.apply {

            toolbar.setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

    }

    private fun showSnackBarWithAction(message: String, actionText: String, onAction: () -> Unit) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setAction(actionText) {
                onAction()
            }
            .show()
    }

    private fun View.showSnackBarWithAction(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .show()
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return emailRegex.matches(email)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MenuHost).removeMenuProvider(menuProvider)
    }

    fun validateEmailsItem(): Boolean {
        binding.apply {
            if (senderNameEditText.text.toString().isEmpty()) {
                senderNameEditText.error = "Add a subject"
                return false
            }

            if (fromEditText.text.toString().isEmpty()) {
                fromEditText.error = "Add valid receivers mail address"
                return false
            }

            if (!isValidEmail(fromEditText.text.toString())){
                fromEditText.error = "Add valid receivers mail address"
                return false
            }


            if (toEditText.text.toString().isEmpty()) {
                toEditText.error = "Add valid senders mail address"
                return false
            }

            if (!isValidEmail(toEditText.text.toString())){
                toEditText.error = "Add valid receivers mail address"
                return false
            }

            if (subjectEditText.text.toString().isEmpty()) {
                subjectEditText.error = "Add a subject"
                return false
            }

            if (messageEditText.text.toString().isEmpty()) {
                messageEditText.error = "Add a message"
                return false
            }
        }
        return true
    }

    /*
        private fun updateConstraint(
        constrainedViewId: Int,
        constrainedSide: Int,
        targetViewId: Int,
        targetSide: Int
    ) {

        val constraintSet = ConstraintSet()
        constraintSet.clone(binding.main)

        constraintSet.connect(
            constrainedViewId, // view to be constraint viewA
            constrainedSide,  // side
            targetViewId,    // constraint to this id viewB
            targetSide    // side of target
        )
        constraintSet.applyTo(binding.main)
    }
     */


}