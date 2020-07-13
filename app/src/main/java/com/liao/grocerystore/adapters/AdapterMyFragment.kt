package com.liao.grocerystore.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.liao.grocerystore.fragment.CategoryFragment

class AdapterMyFragment(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var mFragmentList: ArrayList<Fragment> = ArrayList()
    var mTitleList: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mTitleList.size
    }

    fun addFragment(list: ArrayList<String>?) {
        if (list != null) {
            for (catname in list) {
                mFragmentList.add(CategoryFragment.newInstance(catname))
                mTitleList.add(catname)
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }
}