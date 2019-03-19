package com.roacult.kero.oxxy.projet2eme.di.module

import com.roacult.kero.oxxy.projet2eme.base.AppInitializer
import com.roacult.kero.oxxy.projet2eme.utils.appinitializer.AppIniitializers
import com.roacult.kero.oxxy.projet2eme.utils.appinitializer.FabricInitializer
import com.roacult.kero.oxxy.projet2eme.utils.appinitializer.InstaBugInitializer
import com.roacult.kero.oxxy.projet2eme.utils.appinitializer.StethoInitializer
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
@Module
abstract  class AppModuleBinds {

    @Binds
    @IntoSet
    abstract fun provideFabircInitializer(fabrciInitializer: FabricInitializer): AppInitializer
    @Binds
    @IntoSet
    abstract fun provideStethoInitializer(stethoInitializer: StethoInitializer):AppInitializer
    @Binds
    @IntoSet
    abstract fun provideInstBugInitializer(InstaBugInitializer: InstaBugInitializer):AppInitializer
}