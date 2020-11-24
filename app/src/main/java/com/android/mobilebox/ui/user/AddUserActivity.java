package com.android.mobilebox.ui.user;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mobilebox.R;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.contract.AddUserContract;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.AddUserBody;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.presenter.AddUserPresenter;
import com.android.mobilebox.utils.StringUtils;
import com.android.mobilebox.utils.ToastUtils;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddUserActivity extends BaseActivity<AddUserPresenter> implements AddUserContract.View {
    @BindView(R.id.title_content)
    TextView mTitle;
    @BindView(R.id.iv_face)
    ImageView faceView;
    @BindView(R.id.edit_account)
    EditText accountEt;
    @BindView(R.id.edit_password)
    EditText passwordEt;
    private static final int ACTION_CHOOSE_IMAGE = 0x201;
    private Bitmap mBitmap = null;
    private String path;
    private String imgPath;


    @Override
    public AddUserPresenter initPresenter() {
        return new AddUserPresenter();
    }

    @Override
    protected void initEventAndData() {
        mTitle.setText("添加用户");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adduser;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void handleUploadFace(BaseResponse<UploadFaceResponse> uploadFaceResponse) {
        if (uploadFaceResponse.getCode() == 200000) {
            ToastUtils.showShort("人脸头像上传成功");
            imgPath = uploadFaceResponse.getData().getFaceImage();
        } else {
            imgPath = null;
            ToastUtils.showShort("人脸头像上传失败");
        }
    }

    @Override
    public void handleAddUser(BaseResponse<UserInfo> userInfoResponse) {
        if (userInfoResponse.getCode() == 200000) {
            ToastUtils.showShort("添加用户成功");
        } else {
            ToastUtils.showShort("添加用户失败");
        }
    }

    @OnClick({R.id.iv_face, R.id.bt_add_user, R.id.title_back})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.iv_face:
                chooseLocalImage();
                break;
            case R.id.bt_add_user:
                if (StringUtils.isEmpty(accountEt.getText().toString())) {
                    ToastUtils.showShort("用户名不能为空！");
                    return;
                }
                if (StringUtils.isEmpty(passwordEt.getText().toString())) {
                    ToastUtils.showShort("密码不能为空！");
                    return;
                }
                if (StringUtils.isEmpty(imgPath)) {
                    ToastUtils.showShort("用户头像不能为空！");
                    return;
                }
                AddUserBody addUserBody = new AddUserBody();
                addUserBody.setUsername(accountEt.getText().toString());
                addUserBody.setPassword(passwordEt.getText().toString());
                addUserBody.setFaceImg(imgPath);
                mPresenter.addUser(addUserBody);
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    public void chooseLocalImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, ACTION_CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTION_CHOOSE_IMAGE && resultCode != 0) {
            if (data == null || data.getData() == null) {
                showToast(getString(R.string.get_picture_failed));
                return;
            }
            Uri uriData = data.getData();
            path = getPath(this, uriData);
            try {
                mBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriData);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            if (mBitmap == null) {
                showToast(getString(R.string.get_picture_failed));
                return;
            }
            Glide.with(this)
                    .load(mBitmap)
                    .into(faceView);
            File file = new File(path);
            RequestBody imgBody = RequestBody.create(MediaType.parse("image/*"), file);
            //将文件转化为MultipartBody.Part
            //第一个参数：上传文件的key；第二个参数：文件名；第三个参数：RequestBody对象
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), imgBody);
            mPresenter.uploadFace(filePart);
        }
    }

    public String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"content***"+uri.toString());
            return getDataColumn(context, uri, null, null);
        }
        // Files
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            Log.i(TAG,"file***"+uri.toString());
            return uri.getPath();
        }
        return null;
    }

    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
