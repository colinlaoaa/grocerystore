package com.liao.grocerystore.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.liao.grocerystore.fragment.CategoryFragment
import com.liao.grocerystore.model.Data

class AdapterMyFragment(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var mFragmentList: ArrayList<Fragment> = ArrayList()
    var mTitleList: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mTitleList.size
    }

    fun addFragment(list: ArrayList<Data>?) {
        if (list != null) {
            for (data in list) {
                mFragmentList.add(CategoryFragment.newInstance(data.subId.toString()))
                mTitleList.add(data.subName)
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }
}