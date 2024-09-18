package com.example.digitalbank.presenter.home
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalbank.R
import com.example.digitalbank.data.enum.TransactionOperation
import com.example.digitalbank.data.enum.TransactionType
import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.databinding.TransactionItemBinding
import com.example.digitalbank.util.GetMask

class TransactionAdapter(
    private val context: Context,
    private val transactionSelected: (Transaction) -> Unit
) : ListAdapter<Transaction, TransactionAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(
                oldItem: Transaction,
                newItem: Transaction
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Transaction,
                newItem: Transaction
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TransactionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = getItem(position)

        transaction.operation?.let { //valida se a operacao é nula
            holder.binding.textTrasactionDescription.text = TransactionOperation.getOperation(it)

            holder.binding.textTransactionType.text = TransactionType.getType(it).toString()

            val backgroundColor = if (transaction.type == TransactionType.CASH_IN) {
                ContextCompat.getColor(holder.itemView.context, R.color.color_cashIn)
            } else {
                ContextCompat.getColor(holder.itemView.context, R.color.color_cashOut)
            }

            holder.binding.textTransactionType.backgroundTintList = ColorStateList.valueOf(backgroundColor)

        }



        holder.binding.textTrasactionValue.text = GetMask.getFormatedValue(transaction.amount)
        holder.binding.textTrasactionDate.text = GetMask.getFormatedDate(transaction.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)

        holder.itemView.setOnClickListener {
            Log.d("TransactionAdapter", "Item clicked: ${transaction.id}")
            transactionSelected(transaction)
        }
    }

    inner class ViewHolder(val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}