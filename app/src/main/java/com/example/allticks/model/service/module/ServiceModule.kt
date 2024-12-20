package com.example.allticks.model.service.module

import com.example.allticks.model.service.AccountService
import com.example.allticks.model.service.StorageService
import com.example.allticks.model.service.impl.AccountServiceImpl
import com.example.allticks.model.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
  @Binds abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
  @Binds abstract fun provideStorageService(impl: StorageServiceImpl): StorageService
}
