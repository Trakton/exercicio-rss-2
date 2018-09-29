package br.ufpe.cin.if710.rss.ui

import android.app.Activity
import android.os.Bundle
import android.preference.PreferenceFragment
import br.ufpe.cin.if710.rss.R

class RssFeedPreferenceActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rss_feed_pref_menu)
    }

    class RssFeedPreferenceFragment: PreferenceFragment() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.rss_feed_prefs)
        }
    }
}