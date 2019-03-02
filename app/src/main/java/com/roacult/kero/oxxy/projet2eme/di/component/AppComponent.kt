package com.roacult.kero.oxxy.projet2eme.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import com.roacult.kero.oxxy.projet2eme.AndroidApplication
import com.roacult.kero.oxxy.projet2eme.ui.ActivityBuilder
import com.roacult.kero.oxxy.projet2eme.di.module.AppModule
import com.roacult.kero.oxxy.projet2eme.di.viewmodel.ViewModelModule
import javax.inject.Singleton

/**
 *    Dagger Configuration
 *
 *     in the application we have only this component
 *     and we will use the androidInjector to provides and create subComponent
 *     for each activity and fragment so this component will have the androidinjectormodule that helps us
 *     to inject the application and provide the androidInjector and the modules from data Layer and
 *     the modules of the activity
 *     that will let dagger inject our activity automatiquely
 *     each activity will have a builder which is an abstract class for binding ( @Bind in place of @Provides)
 *    and a module which contain the dependency which the activity needs
 *   and each fragment has a module for the dependency he needs
 *
 * */
@Singleton
@Component(modules = [AndroidInjectionModule::class,
                    ActivityBuilder::class,
                    ViewModelModule::class,
                    AppModule::class])
interface AppComponent : AndroidInjector<AndroidApplication> {
    @Component.Builder
    abstract class Builder: AndroidInjector.Builder<AndroidApplication>()
}