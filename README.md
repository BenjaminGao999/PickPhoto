# PickPhoto
RecyclerView GridLayoutManager ItemDecorationSpace
UI部分重点研究了RecyclerView，GridlayoutManager,ItemDecorationSpace,ImageView列表。OOM发生的原因。
避免OOM，ImageView采用虚引用。以及为什么必须要给ImageView手动设置宽度和高度，并且必须设置。
完美利用ItemDecorationSpace设置item间的space，以及ImageView的宽度值可以设置为屏幕宽度，才能保证item space 完美设置。
以及利用Glide加载本地图片。

本库参考自[PickPhotoSample](https://github.com/Werb/PickPhotoSample)
