package com.imaec.hilotto.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hilotto.Lotto
import com.imaec.hilotto.base.BaseAdapter
import com.imaec.hilotto.databinding.PageLatelyResultBinding
import com.imaec.hilotto.model.LatelyResultDTO
import com.imaec.hilotto.model.LottoDTO
import java.text.DecimalFormat
import kotlin.math.roundToLong

class LatelyResultPageAdapter : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = PageLatelyResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageViewHolder(binding as PageLatelyResultBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PageViewHolder) {
            holder.onBind(listItem[position] as LottoDTO)
        }
    }

    inner class PageViewHolder(private val binding: PageLatelyResultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: LottoDTO) {
            val prizeTotal = if (item.firstAccumamnt > 100000000) {
                "${(item.firstAccumamnt / 100000000.0).roundToLong()}억 원"
            } else {
                "${DecimalFormat("###,###").format(item.firstAccumamnt)} 원"
            }
            val prizeByOne = if (item.firstWinamnt > 100000000) {
                "${(item.firstWinamnt / 100000000.0).roundToLong()}억 원"
            } else {
                "${DecimalFormat("###,###").format(item.firstWinamnt)} 원"
            }
            binding.apply {
                this.item = LatelyResultDTO(
                    "${item.drwNo}회 당첨번호",
                    item.drwNoDate,
                    item.drwtNo1,
                    item.drwtNo2,
                    item.drwtNo3,
                    item.drwtNo4,
                    item.drwtNo5,
                    item.drwtNo6,
                    item.bnusNo,
                    prizeTotal,
                    prizeByOne,
                    "${item.firstPrzwnerCo} 명",
                    Lotto.getSumAvg(listOf(item)),
                    Lotto.getSequenceString(Lotto.getContinues(item.drwtNo1, item.drwtNo2, item.drwtNo3, item.drwtNo4, item.drwtNo5, item.drwtNo6)),
                    "홀 ${Lotto.getOdd(item.drwtNo1, item.drwtNo2, item.drwtNo3, item.drwtNo4, item.drwtNo5, item.drwtNo6).size} / 짝 ${Lotto.getEven(item.drwtNo1, item.drwtNo2, item.drwtNo3, item.drwtNo4, item.drwtNo5, item.drwtNo6).size}"
                )
            }
        }
    }
}