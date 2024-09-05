package com.example.digitalbank.presenter.home
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalbank.data.enum.TransactionOperation
import com.example.digitalbank.data.enum.TransactionType
import com.example.digitalbank.data.model.Transaction
import com.example.digitalbank.databinding.LastTransactionItemBinding
import com.example.digitalbank.util.GetMask

class LastTransactionsAdapter(
    private val transactionSelected: (Transaction) -> Unit
) : ListAdapter<Transaction, LastTransactionsAdapter.ViewHolder>(DIFF_CALLBACK) {

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
            LastTransactionItemBinding.inflate(
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

        }



        holder.binding.textTrasactionValue.text = GetMask.getFormatedValue(transaction.amount)
        holder.binding.textTrasactionDate.text = GetMask.getFormatedDate(transaction.date, GetMask.DAY_MONTH_YEAR_HOUR_MINUTE)
    }

    inner class ViewHolder(val binding: LastTransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}