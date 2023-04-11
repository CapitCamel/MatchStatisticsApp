package com.example.matchstatisticsapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.matchstatisticsapp.databinding.FragmentStatisticBinding

class StatisticFragment : Fragment() {

    private var winsTeam1 = 0
    private var winsTeam2 = 0
    private var draw = 0

    private lateinit var binding: FragmentStatisticBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticBinding.inflate(inflater, container, false)
        binding.btn.setOnClickListener {
            showStatistic()
        }
        return binding.root
    }

    private fun showStatistic(){
        if (binding.editWin1.text.toString() != "") {
            winsTeam1 = binding.editWin1.text.toString().toInt()
        } else winsTeam1 = 0
        if (binding.editWin2.text.toString() != "") {
            winsTeam2 = binding.editWin2.text.toString().toInt()
        } else winsTeam2 = 0
        if (binding.editWin3.text.toString() != "") {
            draw = binding.editWin3.text.toString().toInt()
        } else draw = 0

        val sumMatches = winsTeam1 + winsTeam2 + draw

        if (sumMatches != 0) {
            val x1 = winsTeam1 * 100 / sumMatches
            val x2 = winsTeam2 * 100 / sumMatches
            val x3 = draw * 100 / sumMatches

            binding.statisticProgressBar.setProgress(x1.toFloat(), x2.toFloat(), x3.toFloat())
            binding.tvSum.text = sumMatches.toString()
            binding.wins1Circle.text = winsTeam1.toString()
            binding.wins2Circle.text = winsTeam2.toString()
            binding.wins3Circle.text = draw.toString()
            binding.statistics.visibility = View.VISIBLE
        } else
            binding.statistics.visibility = View.GONE
    }

}