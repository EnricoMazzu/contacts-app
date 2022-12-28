package com.mzzlab.sample.contactsapp.di


import android.content.Context
import com.mzzlab.sample.contactsapp.data.ContactsProvider
import com.mzzlab.sample.contactsapp.data.ContactsRepository
import com.mzzlab.sample.contactsapp.data.impl.ContactsProviderImpl
import com.mzzlab.sample.contactsapp.data.impl.ContactsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ContactsModule {

    @Provides
    @Singleton
    fun provideContactsProvider(@ApplicationContext context: Context): ContactsProvider {
        return ContactsProviderImpl(
            contentResolver = context.contentResolver,
            dispatcher = Dispatchers.IO
        )
    }

    @Provides
    @Singleton
    fun provideContactsRepo(contactsProvider: ContactsProvider): ContactsRepository {
        return ContactsRepositoryImpl(
            contactsProvider = contactsProvider
        )
    }

}