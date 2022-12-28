package com.mzzlab.sample.contactsapp.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

typealias AppFlow<T> = Flow<Result<T>>
typealias MutableAppFlow<T> = MutableStateFlow<Result<T>>