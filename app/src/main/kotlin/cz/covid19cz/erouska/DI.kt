package cz.covid19cz.erouska

import android.app.AlarmManager
import android.app.Application
import android.bluetooth.BluetoothManager
import android.os.PowerManager
import androidx.core.content.getSystemService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.room.Room
import cz.covid19cz.erouska.db.*
import cz.covid19cz.erouska.receiver.BatterSaverStateReceiver
import cz.covid19cz.erouska.receiver.BluetoothStateReceiver
import cz.covid19cz.erouska.receiver.LocationStateReceiver
import cz.covid19cz.erouska.ui.about.AboutVM
import cz.covid19cz.erouska.ui.contacts.ContactsVM
import cz.covid19cz.erouska.ui.dashboard.DashboardVM
import cz.covid19cz.erouska.ui.help.BatteryOptimizationVM
import cz.covid19cz.erouska.ui.help.GuideVM
import cz.covid19cz.erouska.ui.help.HelpVM
import cz.covid19cz.erouska.ui.login.LoginVM
import cz.covid19cz.erouska.ui.main.MainVM
import cz.covid19cz.erouska.ui.mydata.MyDataVM
import cz.covid19cz.erouska.ui.permissions.PermissionDisabledVM
import cz.covid19cz.erouska.ui.permissions.onboarding.PermissionsOnboardingVM
import cz.covid19cz.erouska.ui.sandbox.SandboxVM
import cz.covid19cz.erouska.ui.welcome.WelcomeVM
import cz.covid19cz.erouska.utils.CustomTabHelper
import cz.covid19cz.erouska.utils.DeviceInfo
import cz.covid19cz.erouska.utils.Markdown
import cz.covid19cz.erouska.exposurenotifications.ExposureNotificationsRepo
import cz.covid19cz.erouska.exposurenotifications.db.ExposureNotificationDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainVM() }
    viewModel { SandboxVM(get(), get()) }
    viewModel { LoginVM(get(), get()) }
    viewModel { WelcomeVM(get(), get()) }
    viewModel { HelpVM() }
    viewModel { AboutVM() }
    viewModel { DashboardVM(get()) }
    viewModel { PermissionsOnboardingVM(get(), get()) }
    viewModel { PermissionDisabledVM(get(), get()) }
    viewModel { ContactsVM() }
    viewModel { MyDataVM() }
    viewModel { BatteryOptimizationVM() }
    viewModel { GuideVM() }
}

val databaseModule = module {
    fun provideDatabase(application: Application): ExposureNotificationDatabase {
        return Room.databaseBuilder(application, ExposureNotificationDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { provideDatabase(androidApplication()) }
}

val repositoryModule = module {
    single { SharedPrefsRepository(get()) }
    single { ExposureNotificationsRepo(get()) }
}

val appModule = module {
    single { LocationStateReceiver() }
    single { BluetoothStateReceiver() }
    single { BatterSaverStateReceiver() }
    single { LocalBroadcastManager.getInstance(androidApplication()) }
    single { androidContext().getSystemService<PowerManager>() }
    single { androidContext().getSystemService<BluetoothManager>() }
    single { androidContext().getSystemService<AlarmManager>() }
    single { Markdown(androidContext()) }
    single { DeviceInfo(androidContext()) }
    single { CustomTabHelper(androidContext()) }
}


val allModules = listOf(appModule, viewModelModule, databaseModule, repositoryModule)
