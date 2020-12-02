package com.example.cryptoexample

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoexample.pojo.CryptoCurrency


class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private  var currencies: ArrayList<CryptoCurrency> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item_layout, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currency = currencies[position]
        holder.txtCoin.text = currency.name
        holder.txtMarket.text = currency.market
        holder.txtPrice.text = "$" + String.format("%.2f", currency.price!!.toDouble())
        if (currency.name.equals("eth", ignoreCase = true)) {
            holder.cardView.setCardBackgroundColor(Color.GRAY)
        } else {
            holder.cardView.setCardBackgroundColor(Color.GREEN)
        }
    }

    override fun getItemCount(): Int {
        return currencies.size
    }

    fun setData(data: List<CryptoCurrency>) {
        currencies.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtCoin: TextView = view.findViewById(R.id.txtCoin)
        var txtMarket: TextView = view.findViewById(R.id.txtMarket)
        var txtPrice: TextView = view.findViewById(R.id.txtPrice)
        var cardView: CardView = view.findViewById(R.id.cardView)

    }
}