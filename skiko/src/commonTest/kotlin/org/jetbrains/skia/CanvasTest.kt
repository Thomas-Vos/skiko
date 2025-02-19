@file:Suppress("RemoveRedundantCallsOfConversionMethods")

package org.jetbrains.skia

import org.jetbrains.skia.tests.makeFromResource
import org.jetbrains.skia.util.assertContentSame
import org.jetbrains.skia.util.imageFromIntArray
import org.jetbrains.skiko.tests.runTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertTrue


class CanvasTest {

    @Test
    fun drawVertices() {
        val surface = Surface.makeRasterN32Premul(8, 8)

        val positions = Point.flattenArray(
            arrayOf(
                Point(0.0f, 0.0f),
                Point(4.0f, 4.0f),
                Point(8.0f, 0.0f),
                Point(4.0f, 4.0f),
                Point(0.0f, 8.0f),
                Point(8.0f, 8.0f),
            )
        )!!

        val colors = listOf(
            Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW
        ).toIntArray()

        val indices = shortArrayOf(0, 1, 2, 3, 4, 5)

        surface.canvas.drawVertices(
            VertexMode.TRIANGLES,
            positions,
            colors,
            null,
            indices,
            BlendMode.DST_OVER,
            Paint()
        )

        // Hint: use surface.makeImageSnapshot().printBitmap() to print image as kotlin array
        val expected = imageFromIntArray(intArrayOf(
            0x00000000.toInt(), 0xffbf2020.toInt(), 0xff9f2040.toInt(), 0xff802060.toInt(), 0xff602080.toInt(), 0xff40209f.toInt(), 0xff2020bf.toInt(), 0xff0020df.toInt(),
            0x00000000.toInt(), 0x00000000.toInt(), 0xff806020.toInt(), 0xff606040.toInt(), 0xff406060.toInt(), 0xff206080.toInt(), 0xff00609f.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0xff409f20.toInt(), 0xff209f40.toInt(), 0xff009f60.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0xff00df20.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0xff20ffdf.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0xff60bfdf.toInt(), 0xff60dfbf.toInt(), 0xff60ff9f.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0x00000000.toInt(), 0xff9f80df.toInt(), 0xff9f9fbf.toInt(), 0xff9fbf9f.toInt(), 0xff9fdf80.toInt(), 0xff9fff60.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0xffdf40df.toInt(), 0xffdf60bf.toInt(), 0xffdf809f.toInt(), 0xffdf9f80.toInt(), 0xffdfbf60.toInt(), 0xffdfdf40.toInt(), 0xffdfff20.toInt(),
        ), surface.width)

        assertContentSame(expected, surface.makeImageSnapshot(), 0.25)
    }

    @Test
    fun drawPoints() {
        val surface = Surface.makeRasterN32Premul(8, 8)

        surface.canvas.drawPoints(
            paint = Paint().apply {
                setStroke(true)
                setARGB(0xFF, 0xFF, 0x00, 0x00)
                strokeWidth = 2.0f
            },
            coords = arrayOf(
                Point(1.0f, 1.0f),
                Point(4.0f, 4.0f),
                Point(3.0f, 5.0f),
                Point(2.0f, 6.0f),
                Point(8.0f, 8.0f),
            )
        )

        val expected = imageFromIntArray(intArrayOf(
            0xffff0000.toInt(), 0xffff0000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
            0xffff0000.toInt(), 0xffff0000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0xffff0000.toInt(), 0xffff0000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0x00000000.toInt(), 0xffff0000.toInt(), 0xffff0000.toInt(), 0xffff0000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0xffff0000.toInt(), 0xffff0000.toInt(), 0xffff0000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0xffff0000.toInt(), 0xffff0000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
            0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0xffff0000.toInt(),
        ), surface.width)

        assertContentSame(expected, surface.makeImageSnapshot(), 0.25)
    }

    private suspend fun fontInter36() =
        Font(Typeface.makeFromResource("./fonts/Inter-Hinted-Regular.ttf"), 36f)

    @Test
    fun drawString() = runTest {
        val surface = Surface.makeRasterN32Premul(100, 100)

        val bytes = Bitmap.makeFromImage(surface.makeImageSnapshot()).readPixels()!!
        assertTrue {
            bytes.isNotEmpty() && bytes.all { it == 0.toByte() }
        }

        surface.canvas.drawString(
            s = "Hello world!",
            x = 10f, y = 10f,
            font = fontInter36(),
            paint = Paint().apply {
                color = Color.RED
                setStroke(false)
            }
        )

        val bytes2 = Bitmap.makeFromImage(surface.makeImageSnapshot()).readPixels()!!
        assertTrue {
            bytes2.isNotEmpty() && bytes2.any { it != 0.toByte() }
        }
    }

    @Test
    fun testLocalToDevice() = runTest {
        val surface = Surface.makeRasterN32Premul(100, 100)

        val expectedArray = FloatArray(16) { if (it % 5 == 0) 1f else 0f }
        assertContentEquals(expectedArray, surface.canvas.localToDevice.mat)

        surface.canvas.scale(2f, 2f)

        val expectedAfterScale = floatArrayOf(
            2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f
        )
        assertContentEquals(expectedAfterScale, surface.canvas.localToDevice.mat)

        surface.canvas.resetMatrix()
        assertContentEquals(expectedArray, surface.canvas.localToDevice.mat)
    }

    @Test
    fun testSetMatrix() = runTest {
        val surface = Surface.makeRasterN32Premul(100, 100)

        val expectedArray = FloatArray(16) { if (it % 5 == 0) 1f else 0f }
        assertContentEquals(expectedArray, surface.canvas.localToDevice.mat)

        surface.canvas.setMatrix(Matrix33.makeScale(2f, 2f))

        val expectedAfterScale = floatArrayOf(
            2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f
        )

        assertContentEquals(expectedAfterScale, surface.canvas.localToDevice.mat)
    }

    @Test
    fun drawPatch() = runTest {
        // source: https://fiddle.skia.org/c/e96c5f9aa21fb97c25058d9e7b9be3a9

        val surface = Surface.makeRasterN32Premul(8, 8)

        val points = arrayOf(
            3, 1, 4, 2, 5, 1, 7, 3, 6, 4, 7, 5, 5, 7, 4, 6, 3, 7, 1, 5, 2, 4, 1, 3
        ).toList().chunked(2) {
            Point(it[0].toFloat(), it[1].toFloat())
        }

        surface.canvas.drawPatch(
            cubics = points.toTypedArray(),
            colors = intArrayOf(Color.RED, Color.BLUE, Color.YELLOW, Color.CYAN),
            texCoords = null,
            blendMode = BlendMode.DST_OVER,
            paint = Paint()
        )

        val expected = imageFromIntArray(
            pixArray = intArrayOf(
                0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
                0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0xffcb0634.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
                0x00000000.toInt(), 0x00000000.toInt(), 0xffb63b49.toInt(), 0xff9b3064.toInt(), 0xff762b8a.toInt(), 0xff4b1cb4.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
                0x00000000.toInt(), 0x00000000.toInt(), 0xff8d6e73.toInt(), 0xff866479.toInt(), 0xff7a6386.toInt(), 0xff675698.toInt(), 0xff3632c9.toInt(), 0x00000000.toInt(),
                0x00000000.toInt(), 0xff36cdc9.toInt(), 0xff67a998.toInt(), 0xff7a9c86.toInt(), 0xff869b79.toInt(), 0xff8d9173.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
                0x00000000.toInt(), 0x00000000.toInt(), 0xff4be3b4.toInt(), 0xff76d48a.toInt(), 0xff9bcf64.toInt(), 0xffb6c449.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
                0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0xffcbf934.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),
                0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(), 0x00000000.toInt(),

                ),
            imageWidth = 8
        )

        assertContentSame(expected = expected, got = surface.makeImageSnapshot(), sensitivity = 0.25)
    }


    @Test
    fun testTranslate() {
        val surface = whiteSurface(4, 4)
        surface.canvas.translate(2f, 2f)
        surface.canvas.drawBlackPixel(0, 0)

        surface.assertSingleBlackPixelAt(2, 2)
    }


    @Test
    fun testScale() {
        val surface = whiteSurface(4, 4)
        surface.canvas.scale(2f, 2f)
        surface.canvas.drawBlackPixel(1, 1)

        surface.assertPixelsMatch(
            IntArray(16) { index ->
                when (index) {
                    10, 11, 14, 15 -> 0xff000000.toInt()
                    else -> 0xffffffff.toInt()
                }
            }
        )
    }


    @Test
    fun testRotate() {
        val surface = whiteSurface(4, 4)
        surface.canvas.rotate(deg = 90f)
        surface.canvas.drawBlackPixel(3, -1)

        surface.assertSingleBlackPixelAt(0, 3)
    }


    @Test
    fun testRotateXY() {
        val surface = whiteSurface(4, 4)
        surface.canvas.rotate(deg = 90f, x = 2f, y = 2f)
        surface.canvas.drawBlackPixel(0, 0)

        surface.assertSingleBlackPixelAt(3, 0)
    }


    @Test
    fun testSkew() {
        val surface = whiteSurface(4, 4)
        surface.canvas.skew(sx = 0.5f, sy = 0f)
        surface.canvas.drawBlackPixel(0, 2)

        surface.assertPixelsMatch(
            IntArray(16) { index ->
                when (index) {
                    // Skewing skews the shape of the pixel itself, so it becomes a parallelogram
                    9 -> 0xff3f3f3f.toInt()
                    10 -> 0xffbfbfbf.toInt()
                    else -> 0xffffffff.toInt()
                }
            }
        )
    }

    @Test
    fun testSaveLayerRecRect() {
        val surface = whiteSurface(5, 5)

        surface.canvas.saveLayer(
            Canvas.SaveLayerRec(
                bounds = Rect(1f, 1f, 4f, 4f),
                saveLayerFlags = Canvas.SaveLayerFlags(Canvas.SaveLayerFlagsSet.InitWithPrevious)
            )
        )

        val black = Paint().also { it.setARGB(255, 0, 0, 0) }
        surface.canvas.drawRect(Rect(1f, 1f, 4f, 4f), black)

        surface.canvas.restore()

        surface.assertPixelsMatch(
            intArrayOf(
                Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
                Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE,
                Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE,
                Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE,
                Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
            )
        )
    }


    @Test
    fun testSaveLayerRec() {
        val surface = whiteSurface(5, 5)

        surface.canvas.saveLayer(Canvas.SaveLayerRec(saveLayerFlags = Canvas.SaveLayerFlags(Canvas.SaveLayerFlagsSet.InitWithPrevious)))

        val black = Paint().also { it.setARGB(255, 0, 0, 0) }
        surface.canvas.drawRect(Rect(1f, 1f, 4f, 4f), black)

        surface.canvas.restore()

        surface.assertPixelsMatch(
            intArrayOf(
                Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
                Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE,
                Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE,
                Color.WHITE, Color.BLACK, Color.BLACK, Color.BLACK, Color.WHITE,
                Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE,
            )
        )
    }


    private fun whiteSurface(width: Int, height: Int): Surface {
        val surface = Surface.makeRasterN32Premul(width, height)
        val white = Paint().also { it.setARGB(255, 255, 255, 255) }
        surface.canvas.drawRect(Rect(0f, 0f, width.toFloat(), height.toFloat()), white)
        return surface
    }


    private fun Canvas.drawBlackPixel(x: Int, y: Int) {
        val black = Paint().also { it.setARGB(255, 0, 0, 0) }
        drawRect(Rect(x.toFloat(), y.toFloat(), (x + 1).toFloat(), (y + 1).toFloat()), black)
    }


    private fun Surface.assertPixelsMatch(pixArray: IntArray) {
        assertContentSame(
            expected = imageFromIntArray(
                pixArray = pixArray,
                imageWidth = width
            ),
            got = makeImageSnapshot(),
            sensitivity = 0.0
        )
    }


    private fun Surface.assertSingleBlackPixelAt(x: Int, y: Int) {
        val pixArray = IntArray(width * height) { 0xffffffff.toInt() }
        pixArray[y * width + x] = 0xff000000.toInt()

        assertPixelsMatch(pixArray)
    }
}
