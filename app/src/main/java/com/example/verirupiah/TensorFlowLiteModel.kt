package com.example.verirupiah

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class TensorFlowLiteModel(context: Context, modelPath: String) {
    private val interpreter: Interpreter

    init {
        val options = Interpreter.Options()
        interpreter = Interpreter(loadModelFile(modelPath), options)
    }

    private fun loadModelFile(modelPath: String): ByteBuffer {
        val inputStream = FileInputStream(modelPath)
        val fileChannel = inputStream.channel
        val startOffset = fileChannel.position()
        val declaredLength = fileChannel.size()
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    fun runInference(inputData: FloatArray): FloatArray {
        val outputBuffer = ByteBuffer.allocateDirect(4 * outputTensorSize)
        outputBuffer.order(ByteOrder.nativeOrder())
        interpreter.run(inputData, outputBuffer)
        val outputArray = FloatArray(outputTensorSize)
        outputBuffer.rewind()
        outputBuffer.asFloatBuffer().get(outputArray)
        return outputArray
    }

    companion object {
        private const val outputTensorSize = 4
    }
}
