/**
 * Author Korovkina Valentina
 * Created at 10/04/24
 *
 * */
package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.example.session2.R
import com.example.session2.databinding.FragmentTrackBinding


class TrackFragment : Fragment() {
    private lateinit var binding: FragmentTrackBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentTrackBinding.inflate(inflater,container,false)

        val cards = requireActivity().findViewById<CardView>(R.id.cwindowCard)
        cards.isVisible = false

        return binding.root
    }

}