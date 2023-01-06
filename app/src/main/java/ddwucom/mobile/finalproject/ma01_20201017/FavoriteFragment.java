package ddwucom.mobile.finalproject.ma01_20201017;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.AsyncQueryHandler;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ddwucom.mobile.finalproject.ma01_20201017.medicaldata.pojo.Item;

public class FavoriteFragment extends Fragment {
    final static String TAG = "FavoriteFragment";

    ListView listView;

    ItemDB db;
    ItemDao dao;
    ArrayAdapter<Item> adapter;

    List<Item> itemList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_favorite, container, false);

        listView = view.findViewById(R.id.lv_item);

        itemList = new ArrayList<Item>();
        adapter = new ArrayAdapter<Item>(getActivity(), android.R.layout.simple_list_item_2, android.R.id.text1, itemList) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setText(itemList.get(position).getYadmNm());
                text2.setText(itemList.get(position).getTelno());
                return view;
            }
        };

        db = ItemDB.getDatabase(getActivity());
        dao = db.itemDao();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setOnItemLongClickListener(onItemLongClickListener);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        new getAllItemsTask().execute();
    }

    class getAllItemsTask extends AsyncTask<Void, Void, List<Item>>
    {
        ArrayAdapter<Item> adapter;
        @Override
        protected void onPreExecute() {
            adapter = (ArrayAdapter<Item>) listView.getAdapter();
            itemList.clear();
        }

        @Override
        protected List<Item> doInBackground(Void... params) {
            itemList.addAll(dao.getAllItem());
            for (Item item : itemList) {
                Log.i(TAG, "allItem: " + item.toString());
            }
            return itemList;
        }

        @Override
        protected void onPostExecute(List<Item> itemList) {
            adapter.notifyDataSetChanged();
        }
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Item item = itemList.get(i);
            Intent detailIntent = new Intent(getActivity(), MemoDetailActivity.class);
            detailIntent.putExtra("hospCheck", item);
            startActivity(detailIntent);
        }
    };

    AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            new deleteTask().execute(i);
            return true;
        }
    };

    class deleteTask extends AsyncTask<Integer, Void, List<Item>>
    {
        @Override
        protected List<Item> doInBackground(Integer... params) {
            dao.deleteItem(itemList.get(params[0]));
            itemList.clear();
            itemList.addAll(dao.getAllItem());
            return itemList;
        }
        @Override
        protected void onPostExecute(List<Item> itemList) {
            adapter.notifyDataSetChanged();
        }
    }

}