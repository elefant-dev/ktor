package io.ktor.client.pipeline

import io.ktor.pipeline.Pipeline
import io.ktor.pipeline.PipelineContext
import io.ktor.pipeline.PipelinePhase
import io.ktor.util.safeAs


inline fun <reified NewSubject : Any, Context : Any> Pipeline<*, Context>.intercept(
        phase: PipelinePhase,
        crossinline block: PipelineContext<NewSubject, Context>.(NewSubject) -> Unit) {
    intercept(phase) interceptor@ { subject ->
        subject as? NewSubject ?: return@interceptor
        safeAs<PipelineContext<NewSubject, Context>>()?.block(subject)
    }
}