package com.maskulka.zadanieo2

import android.app.Application
import com.maskulka.zadanieo2.managers.NotificationManager
import com.maskulka.zadanieo2.managers.ScratchCardManager
import com.maskulka.zadanieo2.network.model.CardActivationResponse
import com.maskulka.zadanieo2.network.ScratchCardApi
import com.maskulka.zadanieo2.repository.ScratchCardRepository
import com.maskulka.zadanieo2.ui.activation.ActivationViewModel
import com.maskulka.zadanieo2.ui.dashboard.DashboardFragmentDirections
import com.maskulka.zadanieo2.ui.dashboard.DashboardViewModel
import com.maskulka.zadanieo2.ui.scratch.ScratchViewModel
import com.maskulka.zadanieo2.utils.EMPTY_STRING
import com.maskulka.zadanieo2.utils.getStringResourceId

import org.junit.Assert.*
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java8.En
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.koin.test.KoinTest

@OptIn(ExperimentalCoroutinesApi::class)
class TestSteps : En, KoinTest {


//    @Rule @JvmField
//    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var scratchViewModel: ScratchViewModel
    private lateinit var activationViewModel: ActivationViewModel

    private val application: Application = mockk()
    private val notificationManager: NotificationManager = mockk()
    private val scratchCardManager = ScratchCardManager(application, notificationManager)
    private val api: ScratchCardApi = mockk()

    private lateinit var repository: ScratchCardRepository

    private val activateCardResponseChannel = Channel<Result<CardActivationResponse>>()

    val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = ScratchCardRepository(api, scratchCardManager, CoroutineScope(testDispatcher), CoroutineScope(testDispatcher))

        coEvery { api.activateCard(any()) }.coAnswers { activateCardResponseChannel.receive() }

        every { application.getString(any()) }.answers { EMPTY_STRING }
        every { application.getString(any(), any()) }.answers { EMPTY_STRING }

        every { notificationManager.showNotification(any(), any()) }.answers { EMPTY_STRING }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Given("I am on dashboard")
    fun i_am_on_dashboard() = runTest {
        dashboardViewModel = DashboardViewModel(repository)
    }

    @Given("I am on Scratch screen")
    fun i_am_on_scratch_screen() = runTest {
        scratchViewModel = ScratchViewModel(repository)
    }

    @Given("I am on Activate screen")
    fun i_am_on_activate_screen() = runTest {
        activationViewModel = ActivationViewModel(repository)
    }

    init {
        Given("I open app") {

        }

        And("I see card state {string}") { arg: String ->
            runTest {
                val job = dashboardViewModel.state.onEach {
                    assert(it == arg.getStringResourceId())
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        And("scratch button is {string}") { arg: String ->
            val value = arg == "enabled"
            runTest {
                val job = dashboardViewModel.isScratchButtonEnabled.onEach {
                    assert(it == value)
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        And("activate button is {string}") { arg: String ->
            val value = arg == "enabled"
            runTest {
                val job = dashboardViewModel.isActivateButtonEnabled.onEach {
                    assert(it == value)
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        And("reset button is {string}") { arg: String ->
            val value = arg == "visible"
            runTest {
                val job = dashboardViewModel.isResetButtonVisible.onEach {
                    assert(it == value)
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        When("I click on Scratch button on dashboard") {
            dashboardViewModel.onScratchButtonClick()
        }

        When("I click on Activate button on dashboard") {
            dashboardViewModel.onActivateButtonClick()
        }

        When("I click on Reset button on dashboard") {
            dashboardViewModel.onResetButtonClick()
        }

        When("I'm redirected to Scratch screen") {
            runTest {
                val job = dashboardViewModel.navDirections.onEach {
                    assert(it == DashboardFragmentDirections.navigateToScratchFragment())
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        When("I'm redirected to Activate screen") {
            runTest {
                val job = dashboardViewModel.navDirections.onEach {
                    assert(it == DashboardFragmentDirections.navigateToActivationFragment())
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        When("I click on Scratch button") {
            runTest {
                scratchViewModel.onScratchButtonClick()
                advanceUntilIdle()
            }
        }

        And("I see Scratch init state") {
            runTest {
                val job = scratchViewModel.loadingState.onEach {
                    assert(it == 0)
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        And("I see Scratch loading state") {
            runTest {
                advanceUntilIdle()
                val job = scratchViewModel.loadingState.onEach {
                    assert(it == 1)
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        Then("I see Scratch success state") {
            runTest {
                advanceUntilIdle()
                val job = scratchViewModel.loadingState.onEach {
                    assert(it == 2)
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        When("Scratching of card succeeds") {

        }

        When("I go back to dashboard") {

        }

        And("I see Activate init state") {
            runTest {
                val job = activationViewModel.loadingState.onEach {
                    assert(it == 0)
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        And("I see Activate loading state") {
            runTest {
                val job = activationViewModel.loadingState.onEach {
                    assert(it == 1)
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        When("I click on Activate button") {
            activationViewModel.onActivateButtonClick()
        }

        When("I click on Retry button") {
            activationViewModel.onRetryButtonClick()
        }

        When("Activation of card succeeds with code {string}") { arg: String ->
            activateCardResponseChannel.trySend(Result.success(CardActivationResponse(arg.toInt())))
        }

        Then("I see Activate success state") {
            runTest {
                val job = activationViewModel.loadingState.onEach {
                    assert(it == 2)
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

        Then("I see Activate error state") {
            runTest {
                val job = activationViewModel.loadingState.onEach {
                    assert(it == 3)
                }.launchIn(CoroutineScope(testDispatcher))
                job.cancel()
            }
        }

    }
}