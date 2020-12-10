package com.adt.game_of_life.view.activity.contract

import android.app.Activity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

interface IPermissionActivity {

    fun checkPermission(
        activity: Activity,
        permission: String,
        onGranted: (response: PermissionGrantedResponse) -> Unit = {},
        onDenied: (response: PermissionDeniedResponse) -> Unit = {}
    ) {
        Dexter
            .withActivity(activity)
            .withPermission(permission)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    response?.let(onGranted)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    response?.let(onDenied)
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).check()
    }
}