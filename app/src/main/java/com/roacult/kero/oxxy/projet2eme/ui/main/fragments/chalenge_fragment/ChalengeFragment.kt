package com.roacult.kero.oxxy.projet2eme.ui.main.fragments.chalenge_fragment

import android.animation.Animator
import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.roacult.kero.oxxy.domain.exception.Failure
import com.roacult.kero.oxxy.domain.modules.ChalengeGlobale
import com.roacult.kero.oxxy.projet2eme.R
import com.roacult.kero.oxxy.projet2eme.base.BaseFragment
import com.roacult.kero.oxxy.projet2eme.databinding.MainChalengesBinding
import com.roacult.kero.oxxy.projet2eme.utils.Async
import com.roacult.kero.oxxy.projet2eme.utils.Loading
import com.roacult.kero.oxxy.projet2eme.utils.Success
import com.roacult.kero.oxxy.projet2eme.utils.Fail
import com.roacult.kero.oxxy.projet2eme.utils.extension.visible
import com.roacult.kero.oxxy.projet2eme.utils.extension.invisible
import com.roacult.kero.oxxy.projet2eme.utils.getFilter

class ChalengeFragment : BaseFragment() {
    companion object { fun getInstance() = ChalengeFragment() }
    private lateinit var binding : MainChalengesBinding
    private val viewModel by lazy { ViewModelProviders.of(this,viewModelFactory)[ChalengeViewModel::class.java] }
    private val callback : CallbackFromViewModel by lazy { viewModel }
    private val adapter  :ChalengeAdapter by lazy {ChalengeAdapter(viewModel)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =DataBindingUtil.inflate(inflater,R.layout.main_chalenges,container,false)
        initialaze()

        viewModel.observe(this){
            it.getChalenges?.apply { handleChalenges(this) }
        }

        return binding.root
    }

    private fun handleChalenges(async: Async<List<ChalengeGlobale>>) {
        binding.refresh.setRefreshing(false)
        when(async){
            is Loading -> showLoading()
            is Fail<*,*> ->{
                when(async.error){
                    is Failure.GetAllChalengesFailure.OperationFailed -> showError(R.string.chalenge_cnx_prblm)
                    is Failure.GetAllChalengesFailure.OtherFailrue -> showError(R.string.chalenge_cnx_prblm)
                    is Failure.GetAllChalengesFailure.UserBannedTemp -> showError(R.string.banne_tmp)
                    is Failure.GetAllChalengesFailure.UserBannedForever -> {
                        //TODO show alert dialogue
                    }
                }
            }
            is Success ->{
                val list  = async()
                if(list.size !=0 ) setInRecycler(list)
                else showError(R.string.no_chalenge)
            }
        }
    }

    private fun setInRecycler(items : List<ChalengeGlobale>){
        binding.holder.shimmer.stopShimmer()
        binding.holder.shimmer.animate().apply {
            duration = 300
            alpha(0f)
            setListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    binding.chalengeRecycler.visible()
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
            start()
        }
        binding.noChalenge.invisible()
        adapter.addAll(items)
    }

    private fun showError(@StringRes message: Int) {
        binding.chalengeRecycler.invisible()
        binding.holder.shimmer.stopShimmer()
        binding.noChalenge.setText(message)
        binding.holder.shimmer.animate().apply {
            duration = 300
            alpha(0f)
            setListener(object: Animator.AnimatorListener{
                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
                    binding.noChalenge.visible()
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })
            start()
        }
    }

    private fun showLoading() {
        binding.noChalenge.invisible()
        binding.holder.shimmer.startShimmer()
        binding.holder.shimmer.animate().apply {
            duration = 300
            alpha(1f)
            start()
        }
        binding.chalengeRecycler.invisible()
    }

    private fun initialaze(){
        binding.chalengeRecycler.adapter = adapter
        val manager = LinearLayoutManager(context!!)
        binding.chalengeRecycler.layoutManager = manager
        binding.refresh.setOnRefreshListener {
            callback.requestData()
        }
        setHasOptionsMenu(true)
        (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbar)
    }

    private fun showFilteringDialogue() {
        //initialising view
        val view = LayoutInflater.from(context).inflate(R.layout.main_chalenges_dialogue,null)
        val spinner = view.findViewById<Spinner>(R.id.modules)
        val minPoint = view.findViewById<EditText>(R.id.min_point)
        val maxPoint = view.findViewById<EditText>(R.id.max_point)
        val minQues = view.findViewById<EditText>(R.id.min_ques)
        val maxQues = view.findViewById<EditText>(R.id.max_ques)

        //adding modules to spinner
        val spinnerAdapter  = ArrayAdapter<String>(context!!,android.R.layout.simple_dropdown_item_1line,viewModel.modules)
        spinner.adapter = spinnerAdapter

        //building alert dialogue
        AlertDialog.Builder(context!!).setTitle(R.string.filtering)
            .setNegativeButton(R.string.cancel) { _, _->}
            .setView(view)
            .setPositiveButton(R.string.filter){_,_->
                adapter.filter = getFilter(spinner.selectedItem as String,minPoint.text.toString(),maxPoint.text.toString(),minQues.text.toString(),maxQues.text.toString())
            }
            .show()
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.chalenge_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.filter){
            showFilteringDialogue()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    interface CallbackFromViewModel {
        fun requestData()
    }
}