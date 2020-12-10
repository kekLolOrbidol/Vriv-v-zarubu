package com.adt.game_of_life

import android.app.Application
import com.adt.game_of_life.model.algorithm.IBoardManipulator
import com.adt.game_of_life.model.algorithm.IConwayAlgorithm
import com.adt.game_of_life.model.algorithm.ManipulatorConwayAlgorithm
import com.adt.game_of_life.model.dialog.DialogManager
import com.adt.game_of_life.model.dialog.IDialogManager
import com.adt.game_of_life.model.file.FileManager
import com.adt.game_of_life.model.file.IFileManager
import com.adt.game_of_life.model.pref.IBoardPref
import com.adt.game_of_life.model.pref.IColorsPref
import com.adt.game_of_life.model.pref.IGameRulesPref
import com.adt.game_of_life.model.pref.SharedPrefAccess
import com.adt.game_of_life.model.pref.serializer.GameRulesSerializer
import com.adt.game_of_life.model.pref.serializer.IGameRulesSerializer
import com.adt.game_of_life.model.setting.GameColors
import com.adt.game_of_life.model.setting.GameRules
import com.adt.game_of_life.model.simulation.ILooper
import com.adt.game_of_life.model.simulation.LooperImp
import com.adt.game_of_life.model.simulation.SpeedModel
import com.adt.game_of_life.model.snackbar.ISnackBarManager
import com.adt.game_of_life.model.snackbar.SnackBarManager
import com.adt.game_of_life.viewmodel.GameViewModel
import com.adt.game_of_life.viewmodel.LoadViewModel
import com.adt.game_of_life.viewmodel.MenuViewModel
import com.adt.game_of_life.viewmodel.SettingsViewModel
import com.onesignal.OneSignal
import org.koin.android.ext.android.startKoin
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import timber.log.Timber
import kotlin.random.Random

class GameOfLifeApplication : Application() {

    private val appModule = getApplicationModule()

    override fun onCreate() {
        super.onCreate()
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .init()
        startKoin(this, listOf(appModule))
        setupTimber()
    }

    private fun getApplicationModule(): Module {
        return module {
            single<IGameRulesSerializer> { GameRulesSerializer() }
            single {
                SharedPrefAccess(this@GameOfLifeApplication, get())
            }
                .bind(IGameRulesPref::class)
                .bind(IColorsPref::class)
                .bind(IBoardPref::class)

            single { GameRules(get()) }
            single { GameColors(get()) }

            single {
                val boardPref: IBoardPref = get()
                val height = boardPref.getHeight()
                val width = boardPref.getWidth()
                Array(height) { Array<Int?>(width) { Random.nextInt(0, 2) } }
            }

            single {
                ManipulatorConwayAlgorithm(get(), get())
            } bind IBoardManipulator::class bind IConwayAlgorithm::class

            single<ILooper> { LooperImp() }
            factory { SpeedModel(10000) }

            single<IFileManager> { FileManager(this@GameOfLifeApplication) }
            single<IDialogManager> { DialogManager(get()) }
            single<ISnackBarManager> { SnackBarManager() }

            viewModel { MenuViewModel() }
            viewModel { GameViewModel(get(), get(), get(), get(), get()) }
            viewModel { LoadViewModel(get(), get(), get()) }
            viewModel { SettingsViewModel(get(), get(), get(), get()) }
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}