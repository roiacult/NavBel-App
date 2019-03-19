package com.roacult.kero.oxxy.projet2eme.di.module

import com.roacult.kero.oxxy.projet2eme.base.AppInitializer
import com.roacult.kero.oxxy.projet2eme.utils.appinitializer.AppIniitializers
import com.roacult.kero.oxxy.projet2eme.utils.appinitializer.FabricInitializer
import com.roacult.kero.oxxy.projet2eme.utils.appinitializer.InstaBugInitializer
import com.roacult.kero.oxxy.projet2eme.utils.appinitializer.StethoInitializer
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
abstract  class AppModuleBinds {

    @Binds
    @IntoSet
    @Singleton
    abstract fun provideFabircInitializer(fabrciInitializer: FabricInitializer): AppInitializer
    @Binds
    @IntoSet
    @Singleton
    abstract fun provideStethoInitializer(stethoInitializer: StethoInitializer):AppInitializer
    @Binds
    @IntoSet
    @Singleton
    abstract fun provideInstBugInitializer(InstaBugInitializer: InstaBugInitializer):AppInitializer
}