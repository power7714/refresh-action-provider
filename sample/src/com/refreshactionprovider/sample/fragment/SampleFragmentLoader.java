package com.refreshactionprovider.sample.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.refreshactionprovider.fragment.RefreshListFragment;
import com.refreshactionprovider.sample.R;
import com.refreshactionprovider.sample.loader.SongLoader;
/**
 * RefreshableListFragment, Loader, Custom Layout
 * @author Alexander Gherschon
 *
 */
public class SampleFragmentLoader extends RefreshListFragment implements LoaderCallbacks<List<String>>{

	private static final int LOADER_ID = 1;
	private View mProgressView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
		mProgressView = getView().findViewById(R.id.progress);
		getSherlockActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);	
	}

	@Override
	public Loader<List<String>> onCreateLoader(int id, Bundle bundle) {
		
		setRefreshing(true);
		setListAdapter(null);
		mProgressView.setVisibility(View.VISIBLE);
		return new SongLoader(getSherlockActivity());
	}

	@Override
	public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
		
		setRefreshing(false);
		mProgressView.setVisibility(View.GONE);
		setListAdapter(new ArrayAdapter<String>(getSherlockActivity(), android.R.layout.simple_list_item_1, data));
	}

	@Override
	public void onLoaderReset(Loader<List<String>> loader) {
		
		setListAdapter(null);
	}

	@Override
	public void onRefresh() {
		
		getSherlockActivity().getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
	}
}