package my.destinyStudio.firetimer.screens.imagesScreen


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import my.destinyStudio.firetimer.R
import my.destinyStudio.firetimer.components.IntervalsType
import my.destinyStudio.firetimer.data.ExercisesViewModel
import my.destinyStudio.firetimer.ui.theme.AppColors
import my.destinyStudio.firetimer.utils.generateImageWorkoutA
import java.io.File
import kotlin.math.roundToInt
import my.destinyStudio.firetimer.components.TimePickerCard as TimePickerCard1

@RequiresApi(Build.VERSION_CODES.O)
@Composable
//@Preview
fun ImagesScreen(

 imageDataViewmodel : ImageDataViewmodel = viewModel(),
 exercisesViewModel: ExercisesViewModel= viewModel()
){
    val internalStorageImageFiles by imageDataViewmodel.internalStorageImageFiles.collectAsState()
    val selectedImages by imageDataViewmodel.selectedImageFile.collectAsState()

        var selectedImageUris by remember { mutableStateOf<MutableList<Uri>>(mutableListOf()) }

        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uris: List<Uri>? = result.data?.clipData?.let { clipData ->
                    (0 until clipData.itemCount).map { clipData.getItemAt(it).uri }
                } ?: result.data?.data?.let { listOf(it) }
                uris?.let {
                    selectedImageUris = it.toMutableList()
                    imageDataViewmodel.copyImagesToInternalStorage(context = context, uris = selectedImageUris)
                }
            }
        }

    var offsetX by rememberSaveable {  mutableFloatStateOf(0f) }
    var offsetY by rememberSaveable { mutableFloatStateOf(0f) }
    var nameChange by rememberSaveable { mutableStateOf(false) }
    var createWorkout by rememberSaveable { mutableStateOf(false) }
    var selectedI by rememberSaveable { mutableStateOf<File?>( null) }


    LaunchedEffect(Unit ) {
        imageDataViewmodel.loadImages(context)
        Log.d("A","LoAD PHOTO")

    }

    if(createWorkout){

        WorkoutImageCreateDialog (imagesList =selectedImages , context =  context , dismiss = {createWorkout=false},
            onCreate = {
                sets,wd,wr ->     exercisesViewModel.addWorkOut( generateImageWorkoutA(uris = selectedImages, sets = sets, wD =wd, rD = wr))
                createWorkout=false

            }

        )
    }
    if(nameChange){

        ImageNameChangeAlertDialog(initialName = selectedI?.name, onDismiss = {nameChange=false} ){
imageDataViewmodel.renameFileInInternalStorage(context = context, oldFileName = selectedI?.name.toString(), newFileName = it.toString())
            imageDataViewmodel.loadImages(context)
            nameChange=false
        }
    }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
 floatingActionButton = {
     FloatingActionButton(
         modifier = Modifier
             .size(70.dp)
             .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
             .pointerInput(Unit) {
                 detectDragGestures { change, dragAmount ->
                     change.consume()
                     offsetX += dragAmount.x
                     offsetY += dragAmount.y
                 }
             },
     shape = CircleShape,
     containerColor = AppColors.mOrange ,
     onClick = {   val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
         type = "image/*"
         putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
     }
         launcher.launch(intent)  }

     ) {
         Icon( imageVector =  Icons.Outlined.Image, contentDescription ="", tint = Color.White)  }
             }
        ) {

            paddingV
            ->

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingV)) {

               AnimatedVisibility(
                    modifier = Modifier ,
                    visible =selectedImages.isNotEmpty()  ,
                    enter = slideInHorizontally(),
                    exit =  slideOutHorizontally()
                ) {

                   Column(horizontalAlignment = Alignment.CenterHorizontally) {
                       LazyRow(
                           modifier = Modifier
                               .height(140.dp)
                               .padding(vertical = 5.dp, horizontal = 5.dp),
                           horizontalArrangement = Arrangement.spacedBy(5.dp)

                       ) {
                           itemsIndexed(selectedImages) { index, selectedI ->
                               ImageCardA(
                                   file = selectedI,
                                   context = context,
                                   onDeleteClick = {
                                       imageDataViewmodel.removeImageFromSelected(
                                           index
                                       )
                                   })

                           }

                       }
                       Button(onClick = {  createWorkout = true }) {
                           Text(text = stringResource(R.string.create_workout))
                           
                       }

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(Color.Red)
                    )
                   }
                }


                    LazyVerticalStaggeredGrid(
                        modifier = Modifier.padding(horizontal = 5.dp),
                        columns = StaggeredGridCells.Adaptive(150.dp),
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalItemSpacing = 5.dp

                    ) {
                        itemsIndexed(internalStorageImageFiles) {

                                index, file ->


                            ImageCardA(file = file, context = context, onDeleteClick = {
                                imageDataViewmodel.removeImage(context = context, index = index)

                            },
                                onTextClicked = {
                                    nameChange = true
                                    selectedI = internalStorageImageFiles[index]
                                }

                            )
                            { selectedFile, isSelected
                                ->
                                if (selectedFile !in selectedImages && isSelected) imageDataViewmodel.selectImage(
                                    selectedFile
                                )
                                if (selectedFile in selectedImages && !isSelected) imageDataViewmodel.removeFromSelectedList(
                                    selectedFile
                                )
                            }


                        }
                    }
                }





        }
    }






@Composable
//@Preview
fun ImageNameChangeAlertDialog(initialName: String?="", onDismiss: () -> Unit={}, onConfirm: (String?) -> Unit={} ) {

    var newName by remember {
        mutableStateOf(initialName)
    }
    AlertDialog(onDismissRequest = { onDismiss()},
        confirmButton = {   Button(onClick = { onConfirm(newName) }){   Text(text = stringResource(R.string.update)) }     }
        , dismissButton = {   Button(onClick = { onDismiss() }){  Text(text = stringResource(R.string.dismiss)) }     },
        title = { Text(text = stringResource(R.string.update_name)) },
        text = { TextField(value = newName.toString(), onValueChange = {newName=it }) }

    )

}


@Composable
fun WorkoutImageCreateDialog (imagesList : List<File>,context: Context, onCreate: (Int,Long,Long) -> Unit={_,_,_ ->},dismiss:()-> Unit={ } ){


    var sets by remember {  mutableIntStateOf (1)   }
    var wDuration by remember {  mutableLongStateOf (0)   }
    var rDuration by remember {  mutableLongStateOf (0)   }

    Dialog(onDismissRequest = {dismiss()}) {

        Card  {
            Column(  horizontalAlignment = Alignment.CenterHorizontally) {

                LazyHorizontalGrid(modifier = Modifier
                    .padding(horizontal = 3.dp, vertical = 3.dp)
                    .background(Color.Red)
                    .height(440.dp),
                rows = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                   items (imagesList) {   selectedI ->
                        ImageCardA(
                            file = selectedI,
                            context = context,
                             )

                    }

                }

                Row(modifier = Modifier.padding(horizontal = 3.dp, vertical = 3.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Text(modifier = Modifier.weight(1f), text = IntervalsType.WORK_OUT, textAlign = TextAlign.Center)
                    TimePickerCard1(  secondsInPut = 30,
                        minusColor = Color.Blue, plusColor = Color.Red,

                    ) {
                    wDuration=it
                    }

                }
                Row(modifier = Modifier.padding(vertical= 3.dp, horizontal = 3.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                    Text(modifier = Modifier.weight(1f), text = IntervalsType.REST, textAlign = TextAlign.Center)
                    TimePickerCard1( secondsInPut = 30,
                        minusColor = Color.Blue, plusColor = Color.Red,
                              ) {
                        rDuration=it

                    }

                }
                Row(modifier = Modifier.padding(vertical = 3.dp, horizontal = 3.dp),horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Text(text ="Sets "  )
                    IconButton(onClick = { sets--}) {

                        Icon(imageVector = Icons.Filled.Remove, contentDescription = "")
                    }

                    Text(text = "- $sets -")
                    IconButton(onClick = { sets++ }) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "")

                    }

                }
                Button( modifier = Modifier.padding(vertical = 3.dp),  onClick = { onCreate(sets,wDuration,rDuration)  }) {
                    Text(text =  stringResource(  R.string.create_workout  ) )

                }
            }

        }

    }



}
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ImageCardA(
    file : File,
    context: Context,
    onFavoriteClick: () -> Unit={},
    onDeleteClick: () -> Unit={},
    onTextClicked : () -> Unit={},
    onSelected: (File,Boolean) -> Unit={ _, _ -> }

) {
    var isFavorite  by rememberSaveable { mutableStateOf(false) }
    var isCardSelected by rememberSaveable { mutableStateOf(false) }

   Card(
        modifier = Modifier ,

        onClick = {


        },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp ),
        colors = CardDefaults.cardColors(containerColor = if (isCardSelected )Color.Blue else Color.LightGray)

    ) {
        Column(
            modifier = Modifier
                // .width(200.dp)
                .padding(8.dp)
                .clickable(
                    onClick = { onFavoriteClick() },

                    )

            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    , contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(file.toUri()) // Load image from file URI
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Selected Image",
                 //   placeholder = painterResource(id = R.drawable.crunches),
                    error = painterResource(id = R.drawable.resting)
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)

                ) {
                    IconButton(onClick = { isFavorite  = !isFavorite ; onFavoriteClick() }) {
                        Icon(
                            imageVector = if (isFavorite ) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint =  if (isFavorite ) Color.Red else  Color.LightGray
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = Color.LightGray
                        )
                    }
                }
               IconButton(
                    modifier = Modifier
                        .align(Alignment.TopStart),
                   onClick = {
                       isCardSelected=!isCardSelected
                        onSelected(file,isCardSelected )


                   }

                ){
                   Icon(
                       imageVector = if (isCardSelected )Icons.Rounded.Circle else  Icons.Outlined.Circle, contentDescription = "",

                       tint = if (isCardSelected )Color.Blue else  Color.LightGray


                   )

               }

            }
            Text(modifier = Modifier.clickable {  onTextClicked( )},
                text = file.name,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun ImageCardB(
    file : String,
    context: Context,
    size:Int=100,
   onItemC  : (String) -> Unit={}
) {
    Card(modifier = Modifier
        .size(size.dp)
        .clickable { onItemC(file) }, elevation = CardDefaults.cardElevation(defaultElevation = 5.dp ),
        colors = CardDefaults.cardColors(containerColor =  Color.LightGray)
  ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)  ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                AsyncImage(  modifier = Modifier
                    .fillMaxWidth(),
                    model = ImageRequest.Builder(context)
                        .data(file.toUri()) // Load image from file URI
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Selected Image",
                //    placeholder = painterResource(id = R.drawable.crunches),
                    error = painterResource(id = R.drawable.resting)
                )


            Text(modifier = Modifier ,
                text = file,
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}