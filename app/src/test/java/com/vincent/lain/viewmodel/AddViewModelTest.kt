package com.vincent.lain.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vincent.lain.data.MenuRepository
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddViewModelTest {

    private lateinit var addViewModel: AddViewModel

    @Mock
    lateinit var repository: MenuRepository

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        addViewModel = AddViewModel(repository)
    }

    @Test
    fun cantSaveMenuWithoutTitle() {
        addViewModel.title.set("")
        val canSaveMenu = addViewModel.canSaveMenu()
        assertEquals(false, canSaveMenu)
    }

    @Test
    fun testAddMenuWithTitle() {
        addViewModel.title.set("Greek salad")
        addViewModel.saveMenu()
        assertEquals(true, addViewModel.getSaveLiveData().value)
    }
}