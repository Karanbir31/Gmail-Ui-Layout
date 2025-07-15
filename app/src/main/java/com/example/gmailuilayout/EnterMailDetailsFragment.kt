package com.example.gmailuilayout

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.gmailuilayout.databinding.FragmentEnterMailDetailsBinding
import com.google.android.material.snackbar.Snackbar

class EnterMailDetailsFragment : Fragment() {
    private lateinit var binding: FragmentEnterMailDetailsBinding

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.enter_mail_details_app_bar_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.sendActionMenu -> {

                    binding.root.showSnackBarWithAction("send button click")
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
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(
            menuProvider,
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        binding.apply {
            toEditText.doAfterTextChanged { editable: Editable? ->

                if (!isValidEmail(editable.toString()) && toEmailTextError.visibility != View.VISIBLE) {
                    // show error text view and move ToEditText down ward


                    toEditText.error = "please enter a valid mail address"

                    toEmailTextError.visibility = View.VISIBLE

                    updateConstraint(
                        constrainedViewId = subjectEditText.id,
                        constrainedSide = ConstraintSet.TOP,
                        targetViewId = toEmailTextError.id,
                        targetSide = ConstraintSet.BOTTOM
                    )
                }else  if (isValidEmail(editable.toString()) && toEmailTextError.visibility == View.VISIBLE){

                    toEmailTextError.visibility = View.GONE

                    updateConstraint(
                        constrainedViewId = subjectEditText.id,
                        constrainedSide = ConstraintSet.TOP,
                        targetViewId = toEditText.id,
                        targetSide = ConstraintSet.BOTTOM
                    )
                }

            }

            fromEditText.doAfterTextChanged { editable: Editable? ->

                if (!isValidEmail(editable.toString()) && fromEmailTextError.visibility != View.VISIBLE) {
                    // show error text view and move ToEditText down ward

                    fromEmailTextError.visibility = View.VISIBLE

                    updateConstraint(
                        constrainedViewId = toEditText.id,
                        constrainedSide = ConstraintSet.TOP,
                        targetViewId = fromEmailTextError.id,
                        targetSide = ConstraintSet.BOTTOM
                    )
                } else  if (isValidEmail(editable.toString()) && fromEmailTextError.visibility == View.VISIBLE) {
                    // show error text view and move ToEditText down ward

                    fromEmailTextError.visibility = View.GONE

                    updateConstraint(
                        constrainedViewId = toEditText.id,
                        constrainedSide = ConstraintSet.TOP,
                        targetViewId = fromEditText.id,
                        targetSide = ConstraintSet.BOTTOM
                    )
                }

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

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        return emailRegex.matches(email)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as MenuHost).removeMenuProvider(menuProvider)
    }



}