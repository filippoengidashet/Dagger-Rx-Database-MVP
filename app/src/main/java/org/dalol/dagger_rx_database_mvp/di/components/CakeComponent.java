/*
 * Copyright (c) 2016 Filippo Engidashet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dalol.dagger_rx_database_mvp.di.components;

import org.dalol.dagger_rx_database_mvp.di.module.CakeModule;
import org.dalol.dagger_rx_database_mvp.di.scope.PerActivity;
import org.dalol.dagger_rx_database_mvp.modules.home.MainActivity;

import dagger.Component;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 9/24/2016
 */
@PerActivity
@Component(modules = CakeModule.class, dependencies = ApplicationComponent.class)
public interface CakeComponent {

    void inject(MainActivity activity);
}
